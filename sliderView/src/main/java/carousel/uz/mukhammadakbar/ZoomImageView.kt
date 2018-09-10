package carousel.uz.mukhammadakbar

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

class ZoomImageView(context: Context): AppCompatImageView(context){

    private var isDoubleTap: Boolean = false
    private var isScaled: Boolean = false

    init {
        setOnClickListener { onClick() }
    }

    /**
     *  onDouble tap clicked change image scale (zoom image)
     */
    private fun onClick() {
        if (isDoubleTap) {
            if (!isScaled)
                startAnimation(zoomAnimation(DEFAULT_SCALE, MAX_SCALE))
            else startAnimation(zoomAnimation(MAX_SCALE, DEFAULT_SCALE))
            isScaled = !isScaled
        }
        isDoubleTap = !isDoubleTap
    }

    private fun zoomAnimation(startScale: Float, endScale: Float): ScaleAnimation = ScaleAnimation(
            startScale, endScale, startScale, endScale,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f).apply {
            fillAfter = true
            duration = 300
        }

    /**
     *  for making image scale [DEFAULT_SCALE]
     */
    fun setDefaultState(){
        if (!isScaled)
            startAnimation(zoomAnimation(DEFAULT_SCALE, DEFAULT_SCALE))
        else startAnimation(zoomAnimation(MAX_SCALE, DEFAULT_SCALE))
        isScaled = false
    }

    companion object {
        private const val MAX_SCALE = 1.25f
        private const val DEFAULT_SCALE = 1f
    }
}