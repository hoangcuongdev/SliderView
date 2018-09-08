package carousel.uz.mukhammadakbar

import android.view.View

fun View.visible(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}
