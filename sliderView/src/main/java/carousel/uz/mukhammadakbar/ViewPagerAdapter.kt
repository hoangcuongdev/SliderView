package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatImageView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import carousel.uz.mukhammadakbar.lib.R
import com.bumptech.glide.Glide

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    var imageList = ArrayList<Any>()

    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(p0: View, p1: Any): Boolean = p0 == p1

    fun getDrawable(position: Int) = imageList[position]

    fun addImage(drawable: Drawable?=null,imageUrl: String?=null ,imageUrls: ArrayList<String>? =null) {
        drawable?.let { imageList.add(it) }
        imageUrls?.let {imageList.add(it) }
        imageUrl?.let { imageList.add(it) }
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater : LayoutInflater = context.applicationContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_viewpager, container, false)

        val imageView = itemView.findViewById<AppCompatImageView>(R.id.imageView)

        when(imageList[position]){
            is Drawable ->imageView.setImageDrawable(imageList[position] as Drawable)
            is String -> Glide.with(context).load(imageList[position]).into(imageView)
            else -> throw Exception("mukhammadakbar.uz.SliderView", Throwable("Error while loading image"))
        }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}