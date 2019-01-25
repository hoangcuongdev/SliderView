package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import carousel.uz.mukhammadakbar.adapter.ViewPagerAdapter
import carousel.uz.mukhammadakbar.animation.DepthPageTransformer
import carousel.uz.mukhammadakbar.lib.R
import carousel.uz.mukhammadakbar.model.MockObject
import carousel.uz.mukhammadakbar.views.DotsView

/**
 *  @author Rafiqov Mukhammadakbar (aka markizdeviler)
 *  @modify GreenLove
 *  Image sliding library
 */
class SliderView : RelativeLayout {

    private val viewpager = ViewPager(context)
    private lateinit var dotsLayout : DotsView
    private var pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(context)
    private var sliderHeight: Float? = null
    var setOnSliderItemClickListener: (Any) -> Unit = { }


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

    /**
     *  works when [SliderView] loaded, till [addImage] function worked
     *  shows loading view
     */
    fun addMockObject(){
        pagerAdapter.addMockObject(MockObject())
        viewpager.adapter = pagerAdapter
        invalidate()
    }

    /**
     *  sets Image top & bottom margin
     *  not to the ViewPager
     */
    fun setImageMargin(margin: Int){
        pagerAdapter.setImageMargin(margin)
        invalidate()
    }

    /**
     *  hides blur background of [ViewPager] items
     */
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
//                pagerAdapter.clickListener = {data -> Toast.makeText(context,"$data",Toast.LENGTH_SHORT).show()}
                pagerAdapter.clickListener = {data -> setOnSliderItemClickListener(data)}
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    /**
     *  adds Images to the ViewPager
     *  you can add as Drawable and set image url
     */
    fun addImage(drawable: Drawable?=null,imageUrl: String?=null){
        pagerAdapter.addImage(drawable, imageUrl)
        viewpager.adapter = pagerAdapter
        dotsLayout.addDots(pagerAdapter.count)
        invalidate()
    }
}