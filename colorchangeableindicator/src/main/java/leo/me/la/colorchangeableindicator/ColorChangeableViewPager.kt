package leo.me.la.colorchangeableindicator

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

private val defaultViewPagerBackgroundColors = listOf(
    Color.parseColor("#5545E482"),
    Color.parseColor("#55F9B72B"),
    Color.parseColor("#5543D6C5")
)
class ColorChangeableViewPager : ViewPager, ColorChangeable {
    private val colorEvaluator = ArgbEvaluator()

    var indicatorColors: List<Int> = defaultViewPagerBackgroundColors
        set(value) {
            field = value
            setBackgroundColor(value[currentItem % indicatorColors.size])
        }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        val nextPage = position + 1

        val currentPageColor = indicatorColors[position % indicatorColors.size]
        val nextPageColor = indicatorColors[nextPage % indicatorColors.size]

        val color = colorEvaluator.evaluate(positionOffset, currentPageColor, nextPageColor) as Int
        onColorChanged(color)
    }

    override fun onColorChanged(color: Int) {
        setBackgroundColor(color)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
}
