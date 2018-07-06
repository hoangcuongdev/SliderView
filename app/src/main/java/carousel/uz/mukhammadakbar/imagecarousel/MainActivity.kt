package carousel.uz.mukhammadakbar.imagecarousel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCarousel()
    }

    private fun initCarousel() {
        carousel.addImage(R.drawable.nature1)
        carousel.addImage(R.drawable.nature2)
        carousel.addImage(R.drawable.nature3)
    }

}
