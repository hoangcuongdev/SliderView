package carousel.uz.mukhammadakbar

import android.view.View

/**
 *  Kotlin extension for changing [View] visibility
 */
fun View.visible(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}
