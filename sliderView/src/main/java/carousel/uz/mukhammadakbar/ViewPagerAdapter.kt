package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.webkit.URLUtil
import android.widget.FrameLayout
import android.widget.ImageView
import carousel.uz.mukhammadakbar.lib.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import java.io.FileNotFoundException

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private var imageList = ArrayList<Any>()

    private var margin: Int = 0
    private var isBlurVisible = false

    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(p0: View, p1: Any): Boolean = p0 == p1

    fun addImage(drawable: Drawable?=null,imageUrl: String?=null ,imageUrls: ArrayList<String>? =null) {
        imageList.firstOrNull{it is MockObject}.also { imageList.remove(it) }
        drawable?.let { imageList.add(it) }
        imageUrls?.let {imageList.add(it) }
        imageUrl?.let { imageList.add(it) }
        notifyDataSetChanged()
    }

    fun setImageMargin(margin: Int){
        this.margin = margin
        notifyDataSetChanged()
    }

    fun addMockObject(mockObject: MockObject){
        imageList.add(mockObject)
        Log.d("addMockObject", "addMockObject")
        notifyDataSetChanged()
    }

    fun hideBlurBackground(){
        isBlurVisible = false
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("addMockObject", "instantiateItem")
        val root = FrameLayout(context).apply {
            layoutParams = FrameLayout
                    .LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
        }

        val backgroundView = BlurImageView(context).apply {
            layoutParams = FrameLayout
                    .LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT).apply {
                        gravity = Gravity.CENTER
                    }
            scaleType = ImageView.ScaleType.FIT_XY
        }.also { root.addView(it) }
        backgroundView.visible(isBlurVisible)

        val imageView = ZoomImageView(context).apply {
            layoutParams = FrameLayout
                    .LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                        gravity = Gravity.CENTER
                        setMargins(margin, margin, margin, 2*margin)
                    }
            scaleType = ImageView.ScaleType.FIT_CENTER
        }.also { root.addView(it) }

        val animRotate = RotateAnimation(0f, 359f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f)

        animRotate.duration = 700
        animRotate.interpolator = LinearInterpolator()
        animRotate.repeatCount = RotateAnimation.INFINITE
        animRotate.fillAfter = true

        val progressBar = AppCompatImageView(context).apply {
            layoutParams = FrameLayout
                    .LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                        gravity = Gravity.CENTER
                    }
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_loading))
            animation = animRotate
        }.also { root.addView(it) }


        val loadingText = AppCompatTextView(context).apply {
            layoutParams = FrameLayout
                    .LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                        gravity = Gravity.CENTER
                        topMargin = 40
                    }
            text = context.getString(R.string.loading)
        }.also { root.addView(it) }

        when (imageList[position]) {
            is Drawable -> {
                progressBar.visible(false)
                loadingText.visible(false)
                imageView.setImageDrawable(imageList[position] as Drawable)
                backgroundView.setImageDrawable(imageList[position] as Drawable)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    backgroundView.setBlur()
                }
            }
            is String -> {
                if (URLUtil.isValidUrl(imageList[position] as String)) {
                    Glide.with(context).load(imageList[position])
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(exc: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                    if (exc?.causes?.any { it is FileNotFoundException } == true) {
                                        progressBar.clearAnimation()
                                        progressBar.visible(false)
                                        loadingText.visible(false)
                                    }
                                    return false
                                }

                                override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                    progressBar.clearAnimation()
                                    progressBar.visible(false)
                                    loadingText.visible(false)
                                    backgroundView.setImageDrawable(resource)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                        backgroundView.setBlur()
                                    }
                                    return false
                                }
                            }).into(imageView)
                } else
                    throw Exception("mukhammadakbar.uz.SliderView",
                            Throwable("Image url is not valid, check image url"))
            }

            is MockObject -> {
                //do Nothing
                progressBar.visible(true)
                Log.d("MockObject","added")
            }
            else -> throw Exception("mukhammadakbar.uz.SliderView",
                    Throwable("Error while loading image"))
        }
        container.addView(root)
        return root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}