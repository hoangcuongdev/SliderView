package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.RequiresApi
import android.support.v7.widget.AppCompatImageView

class BlurImageView(context: Context) : AppCompatImageView(context) {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setBlur() {
        val drawable = drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val blurred = blurRenderScript(bitmap, 25)
        setImageBitmap(blurred)
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun blurRenderScript(smallBitmap: Bitmap, radius: Int): Bitmap {
        var smallBitmap = smallBitmap

        try {
            smallBitmap = RGB565toARGB888(smallBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        val bitmap = Bitmap.createBitmap(
                smallBitmap.width, smallBitmap.height,
                Bitmap.Config.ARGB_8888)

        val renderScript = RenderScript.create(context)

        val blurInput = Allocation.createFromBitmap(renderScript, smallBitmap)
        val blurOutput = Allocation.createFromBitmap(renderScript, bitmap)


        ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript)).apply {
            setInput(blurInput)
            setRadius(radius.toFloat())
            forEach(blurOutput)
        }
        blurOutput.copyTo(bitmap)
        renderScript.destroy()

        return bitmap

    }

    @Throws(Exception::class)
    private fun RGB565toARGB888(img: Bitmap): Bitmap {
        val numPixels = img.width * img.height
        val pixels = IntArray(numPixels)

        img.getPixels(pixels, 0, img.width, 0, 0, img.width, img.height)

        val result = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)

        result.setPixels(pixels, 0, result.width, 0, 0, result.width, result.height)
        return result
    }

}