package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.RelativeLayout
import carousel.uz.mukhammadakbar.lib.R

class SliderView : RelativeLayout {

    private val viewpager = ViewPager(context)
    private lateinit var dotsLayout : DotsView
    private var pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(context)
    private var sliderHeight: Float? = null

    constructor(context: Context)
            : super(context) { init(context, null) }

    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs) { init(context, attrs) }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.SliderView)
            sliderHeight = a.getDimension(R.styleable.SliderView_sliderHeight, ViewGroup.LayoutParams.MATCH_PARENT.toFloat())
            dotsLayout = DotsView(context,
                    dotsRadius = a.getDimension(R.styleable.SliderView_dotsRadius, 10f),
                    dotsPadding = a.getDimension(R.styleable.SliderView_dotsPadding, 10f),
                    defaultColor = a.getColor(R.styleable.SliderView_dotsDefaultColor, ContextCompat.getColor(context, R.color.default_dots_color)),
                    selectedColor = a.getColor(R.styleable.SliderView_dotsSelectedColor, ContextCompat.getColor(context, R.color.default_dots_color)))
            a.recycle()
        }

        addViewPager()
        addDotsLayout()
        initViewpagerListener()
    }

    private fun addViewPager() {
        viewpager.layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                sliderHeight?.toInt()?: ViewGroup.LayoutParams.MATCH_PARENT)
        addView(viewpager)
        viewpager.offscreenPageLimit = 5
        viewpager.setPageTransformer(true, DepthPageTransformer())
        viewpager.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
    }

    private fun addDotsLayout() {
        dotsLayout.layoutParams = RelativeLayout
                .LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                .apply {
                    addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    addRule(RelativeLayout.CENTER_HORIZONTAL)
                    bottomMargin = 10
                }
        addView(dotsLayout)
        invalidate()
    }

    fun addMockObject(){
        pagerAdapter.addMockObject(MockObject())
        viewpager.adapter = pagerAdapter
        Log.d("pagerAdapter", "adapter")
        invalidate()
    }

    fun setImageMargin(margin: Int){
        pagerAdapter.setImageMargin(margin)
        invalidate()
    }

    fun hideBlurBackground(){
        pagerAdapter.hideBlurBackground()
        invalidate()
    }

    private fun initViewpagerListener() {
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                dotsLayout.selectDot(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    fun addImage(drawable: Drawable?=null,imageUrl: String?=null ,imageUrls: ArrayList<String>? =null){
        pagerAdapter.addImage(drawable, imageUrl, imageUrls)
        viewpager.adapter = pagerAdapter
        dotsLayout.addDots(pagerAdapter.count)
        invalidate()
    }
}