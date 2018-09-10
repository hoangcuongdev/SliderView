package carousel.uz.mukhammadakbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
        val drawable = getBitmapDrawable(drawable)
        val bitmap = drawable?.bitmap
        val blurred = bitmap?.let { blurRenderScript(it, 25) }
        setImageBitmap(blurred)
    }

    /**
     *  this function prevents showing an error
     *  if drawable is VectorDrawable
     */
    private fun getBitmapDrawable(drawable: Drawable): BitmapDrawable? {
        return try {
            val bitmap: Bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            BitmapDrawable(resources, bitmap)
        } catch (e: OutOfMemoryError) {
            // Handle the error
            null
        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun blurRenderScript(originalBitmap: Bitmap, radius: Int): Bitmap {
        val sBitmap= rgbToArgb(originalBitmap)

        val bitmap = Bitmap.createBitmap(sBitmap.width, sBitmap.height, Bitmap.Config.ARGB_8888)

        val renderScript = RenderScript.create(context)
        val blurInput = Allocation.createFromBitmap(renderScript, sBitmap)
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

    /**
     *  change bitmap to argb888 to rgb565 [Bitmap.Config]
     */
    private fun rgbToArgb(img: Bitmap): Bitmap {
        val numPixels = img.width * img.height
        val pixels = IntArray(numPixels)

        img.getPixels(pixels, 0, img.width, 0, 0, img.width, img.height)

        val result = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)

        result.setPixels(pixels, 0, result.width, 0, 0, result.width, result.height)
        return result
    }
}