package leo.me.la.colorchangeableindicator

import android.content.res.Resources
import android.graphics.Color

internal fun Int.dpToPx() : Int = (this * Resources.getSystem().displayMetrics.density).toInt()

internal fun lightenOrDarken(color: Int, fraction: Double): Int {
    return if (canLighten(color, fraction)) {
        lighten(color, fraction)
    } else {
        darken(color, fraction)
    }
}

internal fun lighten(color: Int, fraction: Double): Int {
    var red = Color.red(color)
    var green = Color.green(color)
    var blue = Color.blue(color)
    red = lightenColor(red, fraction)
    green = lightenColor(green, fraction)
    blue = lightenColor(blue, fraction)
    val alpha = Color.alpha(color)
    return Color.argb(alpha, red, green, blue)
}

internal fun darken(color: Int, fraction: Double): Int {
    var red = Color.red(color)
    var green = Color.green(color)
    var blue = Color.blue(color)
    red = darkenColor(red, fraction)
    green = darkenColor(green, fraction)
    blue = darkenColor(blue, fraction)
    val alpha = Color.alpha(color)

    return Color.argb(alpha, red, green, blue)
}

internal fun canLighten(color: Int, fraction: Double): Boolean {
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return (canLightenComponent(red, fraction)
        && canLightenComponent(green, fraction)
        && canLightenComponent(blue, fraction))
}

internal fun canLightenComponent(colorComponent: Int, fraction: Double): Boolean {
    val red = Color.red(colorComponent)
    val green = Color.green(colorComponent)
    val blue = Color.blue(colorComponent)
    return (red + red * fraction < 255
        && green + green * fraction < 255
        && blue + blue * fraction < 255)
}

internal fun darkenColor(color: Int, fraction: Double): Int {
    return Math.max(color - color * fraction, 0.0).toInt()
}

internal fun lightenColor(color: Int, fraction: Double): Int {
    return Math.min(color + color * fraction, 255.0).toInt()
}
