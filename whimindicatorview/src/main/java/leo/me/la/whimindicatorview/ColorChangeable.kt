package leo.me.la.whimindicatorview

import androidx.annotation.ColorInt
import androidx.viewpager.widget.ViewPager

internal interface ColorChangeable {

    var viewPager: ViewPager?

    fun onColorChangedWhileScrolling(
        @ColorInt color: Int,
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    )
}
