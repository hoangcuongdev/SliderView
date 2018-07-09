package carousel.uz.mukhammadakbar

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import carousel.uz.mukhammadakbar.lib.R

class DotsView : LinearLayout {

    private val dots: ArrayList<CardView> = ArrayList()

    private var defaultColor: Int = 0

    private var selectedColor: Int = 0
    private var dotsPadding: Float = 0f
    private var dotsRadius: Float = 0f
    private var count: Int = 0

    constructor(context: Context):super(context)

    constructor(context: Context, attrs: AttributeSet?):super(context, attrs)

    constructor(context: Context,
                defaultColor: Int?,
                selectedColor: Int?,
                dotsRadius: Float?,
                dotsPadding: Float?): super(context) {
        this.defaultColor = defaultColor?: ContextCompat.getColor(context, R.color.default_dots_color)
        this.selectedColor = selectedColor?: ContextCompat.getColor(context, R.color.selected_dots_color)
        this.dotsRadius = dotsRadius?: 10f
        this.dotsPadding = dotsPadding?: 10f
        init(context, null)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = LinearLayout.HORIZONTAL
    }

    fun selectDot(index: Int) {
        dots.mapIndexed { item, view -> view.setCardBackgroundColor(setColor(index == item)) }
    }

    private fun setColor(isSelected: Boolean): Int {
        return if (isSelected) selectedColor else defaultColor
    }

    fun addDots(count: Int) {
        this.count = count
        for (i in 0 until count) {
            if (dots.size >= count) return
            val dot = CardView(context).apply {
                radius = dotsRadius
                setCardBackgroundColor(defaultColor)
            }

            val params = LinearLayout.LayoutParams(
                    2 * dotsRadius.toInt(),
                    2 * dotsRadius.toInt())
                    .apply {
                        gravity = Gravity.CENTER
                        setMargins(dotsPadding.toInt(), 10, dotsPadding.toInt(), 10)
                    }
            addView(dot, params)
            dots.add(dot)
        }
        invalidate()
        selectDot(0)
        Log.d("dotsCount", "$count: ${dots.size}")
        Log.d("dotsRadius", "dotsR: $dotsRadius")
    }

    @Deprecated(message = "this method will be optimized, permanently use this method")
    fun changeDotsColor(defaultColor: Int? = null, selectedColor: Int? = null){
        if (selectedColor != null) this.selectedColor = selectedColor
        if (defaultColor != null) {
            this.defaultColor = defaultColor
            dots.map { it.setCardBackgroundColor(defaultColor) }
        }
        selectDot(0)
        invalidate()
    }
}