package leo.me.la.colorchangeableindicator

import android.content.Context
import android.util.AttributeSet
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.widget.Button
import java.util.Arrays

internal class ColorChangeableButton : Button, ColorChangeable {

    constructor(context: Context?) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
        : super(context, attrs, defStyleAttr)

    override fun onColorChanged(color: Int) {
        background = getBackgroundDrawable(color)
    }

    private fun getBackgroundDrawable(defaultColor: Int)
        : RippleDrawable {
        return RippleDrawable(
            ColorStateList.valueOf(lightenOrDarken(defaultColor, 0.2)),
            ColorDrawable(defaultColor),
            getRippleColor(defaultColor)
        )
    }

    private fun getRippleColor(color: Int): Drawable {
        val outerRadii = FloatArray(8)
        Arrays.fill(outerRadii, 3f)
        val r = RoundRectShape(outerRadii, null, null)
        val shapeDrawable = ShapeDrawable(r)
        shapeDrawable.paint.color = color
        return shapeDrawable
    }
}
