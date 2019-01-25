package carousel.uz.mukhammadakbar.model.extensions

import android.view.View

/**
 *  Kotlin extension for changing [View] visibility
 */
fun View.visible(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}
