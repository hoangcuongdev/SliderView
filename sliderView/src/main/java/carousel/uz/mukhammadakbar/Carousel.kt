package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import carousel.uz.mukhammadakbar.lib.R

class Carousel : RelativeLayout {

    private val viewpager = ViewPager(context)
    private val linear = LinearLayout(context)
    private var pagerAdapter: ViewPagerAdapter = ViewPagerAdapter(context)
    private val dots: ArrayList<ImageView> = ArrayList()


    constructor(context: Context) : super(context) { init() }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) { init() }

    private fun init() {
        addViewPager()
        addIndicatorLayout()
        initViewpagerListener()
    }

    private fun addViewPager() {
        viewpager.layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
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
                    bottomMargin = 20
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
            val dot = ImageView(context)
            dot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tab_indicator_default))

            val params = LinearLayout.LayoutParams(20, 20)
                    .apply { gravity = Gravity.CENTER
                    setMargins(10,10,10,10)}
            linear.addView(dot, params)
            dots.add(dot)
        }
        invalidate()
        this.toString()
        selectDot(0)
    }

    fun selectDot(idx: Int) {
        val res = resources
        for (i in 0 until pagerAdapter.count) {
            val drawableId = if (i == idx) R.drawable.tab_indicator_selected else R.drawable.tab_indicator_default
            val drawable = ContextCompat.getDrawable(context, drawableId)
            dots[i].setImageDrawable(drawable)
        }
    }

    fun addImage(drawable: Drawable?=null,imageUrl: String?=null ,imageUrls: ArrayList<String>? =null){
        pagerAdapter.addImage(drawable, imageUrl, imageUrls)
        Log.d("addImageFunc", "model: ${drawable.toString()}| ${imageUrl.toString()}|${imageUrls.toString()}")
        viewpager.adapter = pagerAdapter
        addDots()
    }
}

