package leo.me.la.colorchangeableindicator

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.PointF
import android.graphics.Canvas


private const val defaultRadius = 30

internal class AnimatedCircleView : View {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val circle = Circle(paint)
    private var animator: ValueAnimator? = null
    private var _isEnabled = false

    fun enable(enable: Boolean) {
        if (enable) {
            if (_isEnabled)
                return
            _isEnabled = true
            paint.run {
                style = Paint.Style.FILL
                color = selectedColor
            }
            ValueAnimator.ofInt(radius * 2 / 3, radius)
                .setDuration(300)
                .apply {
                    addUpdateListener {
                        circle.currentRadius = it.animatedValue as Int
                        invalidate()
                    }
                }
                .also {
                    animator = it
                }
                .start()
        } else {
            _isEnabled = false
            paint.run {
                style = Paint.Style.STROKE
                strokeWidth = 5f
                color = disableColor
            }
            animator?.cancel()
            circle.currentRadius = radius - paint.strokeWidth.toInt() /2
            invalidate()
        }
    }

    var radius: Int = defaultRadius
        set(value) {
            require(value >= 0) { "Negative radius is forbidden" }
            field = value
            invalidate()
        }
    var selectedColor: Int = enableColor
        set(value) {
            field = value
            if (_isEnabled) {
                paint.color = value
                invalidate()
            }
        }

    constructor(context: Context?) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = 2 * radius + 2
        val desiredWidth = 2 * radius + 2
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
        circle.setCenter(width / 2f, height / 2f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        circle.draw(canvas, Math.min(width, height) / 2f)
    }

    inner class Circle(private val paint: Paint) {

        private val center = PointF()

        var currentRadius: Int = defaultRadius

        fun setCenter(x: Float, y: Float) {
            center.set(x, y)
        }

        fun draw(canvas: Canvas, limitedRadius: Float) {
            canvas.drawCircle(
                center.x,
                center.y,
                Math.min(currentRadius.toFloat(), limitedRadius),
                paint
            )
        }
    }
}
