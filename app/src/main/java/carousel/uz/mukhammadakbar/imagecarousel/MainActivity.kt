package carousel.uz.mukhammadakbar.imagecarousel

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_main.*
import android.view.animation.ScaleAnimation



class MainActivity : AppCompatActivity() {

    var isScrolled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCarousel()
        initCarousels()
        carousel.attachToScrollView(nestedScrollView)
    }

    private fun initCarousel() {
        carousel.addImage(imageUrl = "http://apibazarway.wienerdeming.com/media/file/image/2018-06/0d02aea0-71d5-4c51-adb4-e4ea2007e27e.jpg.80x80_q85_crop.jpg")
        carousel.addImage(imageUrl = "http://apibazarway.wienerdeming.com/media/file/image/2018-06/0d02aea0-71d5-4c51-adb4-e4ea2007e27e.jpg.80x80_q85_crop.jpg")
        carousel.addImage(imageUrl = "http://apibazarway.wienerdeming.com/media/file/image/2018-05/f3b193f1-7b04-4034-b29a-861d1b6f3ebb.jpg.80x80_q85_crop.jpg")
    }

    private fun initCarousels() {
        carousel.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.product_fake2))
        carousel.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.product_fake))
        carousel.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.product_fake))
    }
}