package carousel.uz.mukhammadakbar

import android.os.Build
import android.support.v4.view.ViewPager
import android.view.View

/**
 *  PageTransformer for [ViewPager]
 *  used [View.TRANSLATION_X] & [View.TRANSLATION_Y] & [ViewPager.TRANSLATION_Z]
 */
class DepthPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width

        when {
            position < -1 ->
                view.alpha = 0f
            position <= 0 -> {
                view.alpha = 1f
                view.translationX = 0f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.translationZ = 1f
                }
                view.translationY = 1f // fghj

                view.scaleX = 1f
                view.scaleY = 1f
            }
            position <= 1 -> {
                view.alpha = 1 - position
                view.translationY = 1-position
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.translationZ = pageWidth * -position
                }
                view.translationX = pageWidth * -position
                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            else -> {
                view.alpha = 0f
                view.translationY = 0f
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.translationZ = 0f
                }
            }
        }
    }

    companion object {
        private const val MIN_SCALE = 0.75f
    }
}