package leo.me.la.colorchangeableindicator

import androidx.annotation.ColorInt

internal interface ColorChangeable {
    fun onColorChanged(@ColorInt color: Int)
}
