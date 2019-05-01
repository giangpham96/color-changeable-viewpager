package leo.me.la.colorchangeableviewpager

import androidx.annotation.ColorInt

internal interface ColorChangeable {
    fun onColorChanged(@ColorInt color: Int)
}
