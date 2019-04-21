package leo.me.la.colorchangeableindicator

import androidx.annotation.ColorInt

internal interface ColorChangeable {

    fun onColorChangedWhileScrolling(
        @ColorInt color: Int,
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    )
}
