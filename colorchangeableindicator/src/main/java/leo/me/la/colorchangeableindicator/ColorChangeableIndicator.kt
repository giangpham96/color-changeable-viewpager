package leo.me.la.colorchangeableindicator

import android.animation.ArgbEvaluator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.viewpager.widget.ViewPager

private val defaultIndicatorColors = listOf(
    Color.parseColor("#45E482"),
    Color.parseColor("#F9B72B"),
    Color.parseColor("#43D6C5")
)
const val defaultTriangleWidth = 50
const val defaultTriangleHeight = 30

class ColorChangeableIndicator : LinearLayout, ColorChangeable, ViewPager.OnPageChangeListener {
    private val colorEvaluator = ArgbEvaluator()

    private lateinit var button: ColorChangeableButton

    private lateinit var colorChangeableArrow: ColorChangeableArrow

    private lateinit var tabs: LinearLayout

    var indicatorColors: List<Int> = defaultIndicatorColors
        set(value) {
            field = value
            invalidate()
        }

    var text: String
        get() = button.text.toString()
        set(value) {
            button.text = value
        }

    var textSize: Float
        get() = button.textSize
        set(value) {
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }

    var tabTextSize: Float = 12.dpToPx().toFloat()
        set(value) {
            field = value
            tabs.forEach {
                (it as SelectableTab).textSize = value
            }
        }

    var selectedColor: Int = enableColor
        set(value) {
            field = value
            tabs.forEach {
                (it as SelectableTab).selectedColor = value
            }
        }

    fun integrateWithViewPager(viewPager: ViewPager) {
        check(viewPager.adapter != null || viewPager.adapter !is ColorChangeablePagerAdapter) {
            "$viewPager must have a ${ColorChangeablePagerAdapter::class.java.simpleName} as its adapter"
        }
        val adapter = viewPager.adapter as ColorChangeablePagerAdapter
        adapter.run {
            colorChangeableArrow.adapter = this
        }
        viewPager.run {
            addOnPageChangeListener(this@ColorChangeableIndicator)
            setCurrentItem(0, true)
        }
        tabs.run {
            removeAllViews()
            for(position in 0 until adapter.count) {
                addView(
                    SelectableTab(context).apply {
                        layoutParams = LayoutParams(0, WRAP_CONTENT)
                            .apply {
                                weight = 1f
                            }
                        textSize = this@ColorChangeableIndicator.tabTextSize
                        text = adapter.getTitle(position)
                        circleRadius = adapter.getCircleIndicatorSize(position)
                        selectedColor = this@ColorChangeableIndicator.selectedColor
                    }
                )
            }
        }
        onPageSelected(0)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context.obtainStyledAttributes(attrs, R.styleable.ColorChangeableIndicator))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr) {
        init(context.obtainStyledAttributes(attrs, R.styleable.ColorChangeableIndicator))
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
        forEach {
            if (it is ColorChangeable)
                it.onColorChangedWhileScrolling(color, position, positionOffset, positionOffsetPixels)
        }
    }

    override fun onPageSelected(position: Int) {
        tabs.forEachIndexed { index, view ->
            (view as SelectableTab).run {
                view.enable(index == position)
            }
        }
    }

    private fun init(obtainStyledAttributes: TypedArray) {
        orientation = VERTICAL
        View.inflate(context, R.layout.color_changeable_indicator, this)
        isClickable = false
        isFocusable = false
        colorChangeableArrow = findViewById(R.id.downArrowIndicator)
        button = findViewById(R.id.button)
        tabs = findViewById(R.id.tabs)

        text = obtainStyledAttributes.getString(R.styleable.ColorChangeableIndicator_text) ?: ""

        colorChangeableArrow.setTriangleHeight(
            obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.ColorChangeableIndicator_indicatorTriangleHeight,
                defaultTriangleHeight
            )
        )

        colorChangeableArrow.setTriangleWidth(
            obtainStyledAttributes.getDimensionPixelSize(
                R.styleable.ColorChangeableIndicator_indicatorTriangleWidth,
                defaultTriangleWidth
            )
        )

        textSize = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.ColorChangeableIndicator_textSize,
            12.dpToPx()
        ).toFloat()

        tabTextSize = obtainStyledAttributes.getDimensionPixelSize(
            R.styleable.ColorChangeableIndicator_tabTextSize,
            12.dpToPx()
        ).toFloat()

        selectedColor = obtainStyledAttributes.getColor(
            R.styleable.ColorChangeableIndicator_selectedColor,
            enableColor
        )

        obtainStyledAttributes.recycle()
    }
}
