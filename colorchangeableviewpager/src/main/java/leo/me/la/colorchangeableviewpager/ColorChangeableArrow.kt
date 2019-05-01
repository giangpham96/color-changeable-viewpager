package leo.me.la.colorchangeableviewpager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View

internal class ColorChangeableArrow : View, ColorChangeable, PageScrollObserver {
    var adapter: ColorChangeablePagerAdapter? = null

    private val colorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

    private val triangle = IsoscelesTriangle(colorPaint)

    constructor(context: Context?) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredHeight = triangle.height
        val desiredWidth = triangle.width
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> Math.min(desiredHeight, heightSize)
            else -> desiredHeight
        }
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(desiredWidth, widthSize)
            else -> desiredWidth
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        triangle.draw(canvas, height)
    }

    override fun onColorChanged(color: Int) {
        colorPaint.color = color
    }

    override fun onPageScrolled(position: Int, positionOffset: Float) {
        adapter?.run {
            triangle.setBottomMidpointCoordinator(
                width * ((position + positionOffset) * 2 + 1) / (count * 2),
                0f
            )
            invalidate()
        }
    }

    fun setTriangleWidth(width: Int) {
        triangle.width = width
        invalidate()
    }

    fun setTriangleHeight(height: Int) {
        triangle.height = height
        invalidate()
    }

    inner class IsoscelesTriangle(private val paint: Paint) {
        private val path = Path()
        private val bottomMidpoint = PointF()
        var width = defaultTriangleWidth
        var height = defaultTriangleHeight

        fun setBottomMidpointCoordinator(x: Float, y: Float) {
            bottomMidpoint.set(x, y)
        }

        fun draw(canvas: Canvas, heightLimitation: Int) {
            path.apply {
                reset()
                moveTo(bottomMidpoint.x - width / 2, bottomMidpoint.y)
                lineTo(bottomMidpoint.x + width / 2, bottomMidpoint.y)
                lineTo(bottomMidpoint.x, bottomMidpoint.y + Math.min(heightLimitation, height))
                close()
            }
            canvas.drawPath(path, paint)
        }
    }
}
