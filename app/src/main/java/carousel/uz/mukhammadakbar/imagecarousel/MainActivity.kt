package carousel.uz.mukhammadakbar.imagecarousel

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCarousel()
        initCarousels()
    }

    private fun initCarousel() {
        sliderView.setImageMargin(20)
        sliderView.hideBlurBackground()
        sliderView.addMockObject()
        sliderView.addImage(imageUrl = "http://apibazarway.wienerdeming.com/media/file/image/2018-08/948321c9-db06-4c78-a7e7-aba36e2208ef.jpg")
        sliderView.addImage(imageUrl = "http://apibazarway.wienerdeming.com/media/file/image/2018-08/91c984bc-e7a3-42a2-8c32-2e88ebb5c045.jpg")
    }

    private fun initCarousels() {
        sliderView.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.product_fake))
        sliderView.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.product_fake2))
        sliderView.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.nature3))
    }
}