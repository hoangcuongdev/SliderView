package carousel.uz.mukhammadakbar.imagecarousel

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 *  @author Rafiqov Mukhammadakbar (aka markizdeviler)
 *  @modify GreenLove
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCarousel()
        sliderView.setOnSliderItemClickListener = {data -> Toast.makeText(this,"Click|===> $data",Toast.LENGTH_SHORT).show()}
    }

    private fun initCarousel() {
        sliderView.setImageMargin(15)
        sliderView.addMockObject()
        sliderView.addImage(imageUrl = "http://apibazarway.wienerdeming.com/media/file/image/2018-08/948321c9-db06-4c78-a7e7-aba36e2208ef.jpg")
        sliderView.addImage(imageUrl = "http://apibazarway.wienerdeming.com/media/file/image/2018-08/91c984bc-e7a3-42a2-8c32-2e88ebb5c045.jpg")
        sliderView.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_no_product))
        sliderView.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.nature1))
        sliderView.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.nature2))
        sliderView.addImage(drawable = ContextCompat.getDrawable(applicationContext, R.drawable.nature3))
    }
}