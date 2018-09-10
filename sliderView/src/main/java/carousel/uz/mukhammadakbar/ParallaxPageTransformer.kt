package carousel.uz.mukhammadakbar

import android.support.v4.view.ViewPager
import android.view.View

class ParallaxPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {

        val pageWidth = view.width

        if (position > 0){
            view.translationX = -position * (pageWidth /2).toInt() //Half the normal speed
        }else {
            view.translationX = position * (pageWidth /2).toInt() //Half the normal speed
        }
    }
}