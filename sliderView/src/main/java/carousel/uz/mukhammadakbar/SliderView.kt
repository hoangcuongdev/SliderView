package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.RelativeLayout
import carousel.uz.mukhammadakbar.lib.R

class SliderView : RelativeLayout {

    private val viewpager = ViewPager(context)
    private lateinit var dotsLayout : DotsView
    private var pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(context)
    private lateinit var nestedScrollView: NestedScrollView
    private var sliderHeight: Float? = null
    private var isScrolled = false
    var DURATION = 400.toLong()
    var SCALE_X: Float = 0.8f
    var SCALE_Y: Float = 0.8f

    constructor(context: Context)
            : super(context) { init(context, null) }

    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs) { init(context, attrs) }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.SliderView)
            sliderHeight = a.getDimension(R.styleable.SliderView_sliderHeight, ViewGroup.LayoutParams.MATCH_PARENT.toFloat())
            dotsLayout = DotsView(
                    context,
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

    private fun initViewpagerListener() {
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                dotsLayout.selectDot(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun addImage(drawable: Drawable?=null,imageUrl: String?=null ,imageUrls: ArrayList<String>? =null){
        pagerAdapter.addImage(drawable, imageUrl, imageUrls)
        viewpager.adapter = pagerAdapter
        dotsLayout.addDots(pagerAdapter.count)
        invalidate()
    }

    fun attachToScrollView(scrollView: NestedScrollView){
        nestedScrollView = scrollView
        initScrollingListener()
    }

    private fun initScrollingListener() {
        nestedScrollView.setOnScrollChangeListener { nestedScrollView: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int ->
            if (y - oldY > 0 && !isScrolled){ // scroll to bottom
                scaleView(this, 1f, SCALE_X, 1f, SCALE_Y)
                isScrolled = true
            }
            if(y - oldY < 0 && isScrolled){ // scroll to top
                scaleView(this,  SCALE_X, 1f, SCALE_Y, 1f)
                isScrolled = false
            }
        }
    }

    private fun scaleView(v: View, startScaleX: Float, endScaleX: Float,
                          startScaleY: Float, endScaleY: Float) {
        val anim = ScaleAnimation(
                startScaleY, endScaleY, // Start and end values for the X axis scaling
                startScaleX, endScaleX, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f) // Pivot point of Y scaling
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = DURATION
        v.startAnimation(anim)
    }
}