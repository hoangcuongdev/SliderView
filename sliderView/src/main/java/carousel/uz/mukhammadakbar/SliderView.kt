package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import carousel.uz.mukhammadakbar.lib.R

class SliderView : RelativeLayout {

    private val viewpager = ViewPager(context)
    private val linear = LinearLayout(context)
    private var pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(context)
    private val dots: ArrayList<CardView> = ArrayList()
    private var sliderHeight: Float? = null
    private var defaultColor: Int? = null
    private var selectedColor: Int? = null
    private var dotsRadius: Float? = null
    private var dotsPadding: Float? = null


    constructor(context: Context) : super(context) { init(context, null) }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        Log.d("attr", attrs.toString())

        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.SliderView)
            sliderHeight = a.getDimension(R.styleable.SliderView_sliderHeight, ViewGroup.LayoutParams.MATCH_PARENT.toFloat())
            dotsRadius = a.getDimension(R.styleable.SliderView_dotsRadius, 10f)
            dotsPadding = a.getDimension(R.styleable.SliderView_dotsPadding, 10f)
            defaultColor = a.getColor(R.styleable.SliderView_dotsDefaultColor, ContextCompat.getColor(context, R.color.default_dots_color))
            selectedColor = a.getColor(R.styleable.SliderView_dotsSelectedColor, ContextCompat.getColor(context, R.color.default_dots_color))
            a.recycle()
        }

        addViewPager()
        addIndicatorLayout()
        initViewpagerListener()
    }

    private fun addViewPager() {
        viewpager.layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                sliderHeight?.toInt()?: ViewGroup.LayoutParams.MATCH_PARENT)
        addView(viewpager)
        viewpager.offscreenPageLimit = 5
    }

    private fun addIndicatorLayout() {
        linear.layoutParams = RelativeLayout
                .LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                .apply {
                    addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    addRule(RelativeLayout.CENTER_HORIZONTAL)
                    bottomMargin = 10
                }
        linear.orientation = LinearLayout.HORIZONTAL
        addView(linear)
        invalidate()
    }

    private fun initViewpagerListener() {
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                selectDot(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun addDots() {
        for (i in 0 until pagerAdapter.count) {
            if (dots.size >= pagerAdapter.count) return
            val dot = CardView(context)
            dot.radius = dotsRadius?:10f
            dot.setCardBackgroundColor(ContextCompat.getColor(context, R.color.default_dots_color))

            val params = LinearLayout.LayoutParams(
                    2*(dotsRadius?:10f).toInt(),
                    2*(dotsRadius?:10f).toInt())
                    .apply { gravity = Gravity.CENTER
                    setMargins(dotsPadding?.toInt()?: 10,10,dotsPadding?.toInt()?:10,10)}
            linear.addView(dot, params)
            dots.add(dot)
        }
        invalidate()
        this.toString()
        selectDot(0)
    }

    fun selectDot(idx: Int) {
        for (i in 0 until pagerAdapter.count) {
            val colorId = if (i == idx) {
                selectedColor?: ContextCompat.getColor(context, R.color.selected_dots_color)
            } else {
                defaultColor?: ContextCompat.getColor(context, R.color.default_dots_color)
            }
            dots[i].setCardBackgroundColor(colorId)
        }
    }

    fun addImage(drawable: Drawable?=null,imageUrl: String?=null ,imageUrls: ArrayList<String>? =null){
        pagerAdapter.addImage(drawable, imageUrl, imageUrls)
        viewpager.adapter = pagerAdapter
        addDots()
    }

    fun changeDotsColor(defaultColor: Int? = null, selectedColor: Int? = null){
        this.defaultColor = defaultColor
        this.selectedColor = selectedColor

        if (defaultColor != null) {
            dots.map {
                it.setCardBackgroundColor(defaultColor)
            }
        }
        selectDot(0)
        invalidate()
    }
}