package leo.me.la.colorchangeableviewpager

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

val disableColor = Color.parseColor("#C9B9B9B9")
val enableColor = Color.parseColor("#2F2969")

internal class SelectableTab(context: Context) : LinearLayout(context) {

    private var textView: TextView
    private var circle: AnimatedCircleView

    private var _isEnabled = false

    var selectedColor: Int = enableColor
        set(value) {
            field = value
            circle.selectedColor = value
            if (_isEnabled) {
                textView.setTextColor(selectedColor)
            }
        }

    var text: String
        get() = textView.text.toString()
        set(value) {
            textView.text = value
        }

    var textSize: Float
        get() = textView.textSize
        set(value) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, value)
        }

    var circleRadius: Int
        get() = circle.radius
        set(value) {
            circle.radius = value
        }

    init {
        View.inflate(context, R.layout.selectable_tab, this)
        gravity = Gravity.CENTER
        orientation = VERTICAL
        textView = findViewById(R.id.textview)
        circle = findViewById(R.id.circle)
    }

    fun enable(enable: Boolean) {
        circle.enable(enable)
        if (enable) {
            textView.setTextColor(selectedColor)
        } else {
            textView.setTextColor(disableColor)
        }
    }
}
