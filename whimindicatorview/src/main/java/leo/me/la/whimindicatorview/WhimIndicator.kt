package leo.me.la.whimindicatorview

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import java.lang.IllegalArgumentException

private val defaultIndicatorColors = listOf(
    Color.parseColor("#CEEFFB"),
    Color.parseColor("#2F2969"),
    Color.parseColor("#F6EC36")
)
const val defaultTriangleWidth = 50
const val defaultTriangleHeight = 30

class WhimIndicator : LinearLayout, ColorChangeable, ViewPager.OnPageChangeListener {
    private val colorEvaluator = ArgbEvaluator()

    private lateinit var button: ColorChangeableButton

    private lateinit var downArrowIndicator: DownArrowIndicator

    var indicatorColors: List<Int> = defaultIndicatorColors
        set(value) {
            field = value
            invalidate()
        }

    var text: String = ""
        set(value) {
            field = value
            button.text = value
        }
    var textSize: Float = 12.dpToPx().toFloat()
        set(value) {
            field = value
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }

    override var viewPager: ViewPager? = null
        set(value) {
            field?.removeOnPageChangeListener(this)
            if (value == null) {
                throw IllegalArgumentException("Must pass a non-null value")
            }
            downArrowIndicator.viewPager = value
            button.viewPager = value
            field = value
            value.addOnPageChangeListener(this)
            value.setCurrentItem(0, true)
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context.obtainStyledAttributes(attrs, R.styleable.WhimIndicator))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
        init(context.obtainStyledAttributes(attrs, R.styleable.WhimIndicator))
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val nextPage = position + 1

        val currentPageColor = indicatorColors[position % indicatorColors.size]
        val nextPageColor = indicatorColors[nextPage % indicatorColors.size]

        val color = colorEvaluator.evaluate(positionOffset, currentPageColor, nextPageColor) as Int
        onColorChangedWhileScrolling(color, position, positionOffset, positionOffsetPixels)
    }

    override fun setOnClickListener(l: OnClickListener) = button.setOnClickListener(l)

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onColorChangedWhileScrolling(
        color: Int,
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
        downArrowIndicator
            .onColorChangedWhileScrolling(color, position, positionOffset, positionOffsetPixels)
        button.onColorChangedWhileScrolling(color, position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {}

    private fun init(obtainStyledAttributes: TypedArray) {
        orientation = VERTICAL
        View.inflate(context, R.layout.color_changeable_indicator, this)
        isClickable = false
        isFocusable = false
        downArrowIndicator = findViewById(R.id.downArrowIndicator)
        button = findViewById(R.id.button)

        text = obtainStyledAttributes.getString(R.styleable.WhimIndicator_text) ?: ""

        downArrowIndicator.setTriangleHeight(
            obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.WhimIndicator_indicatorTriangleHeight,
                defaultTriangleHeight
            )
        )

        downArrowIndicator.setTriangleWidth(
            obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.WhimIndicator_indicatorTriangleWidth,
                defaultTriangleWidth
            )
        )

        textSize = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.WhimIndicator_textSize,
            12.dpToPx()
        ).toFloat()

        obtainStyledAttributes.recycle()
    }
}
