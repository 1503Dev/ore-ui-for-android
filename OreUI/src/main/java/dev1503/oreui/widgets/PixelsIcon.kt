package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dev1503.oreui.Pixels2D

class PixelsIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }

    var pixelSize: Float = 4f
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    var pixels2d: Pixels2D? = null
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    var color: Int = 0xFFFFFFFF.toInt()
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val p2d = pixels2d
        if (p2d == null) {
            setMeasuredDimension(0, 0)
            return
        }

        val w = (p2d.width * pixelSize).toInt()
        val h = (p2d.height * pixelSize).toInt()
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        val p2d = pixels2d ?: return
        paint.color = color

        p2d.pixels.forEach { packed ->
            val px = (packed shr 32).toInt()
            val py = (packed and 0xFFFFFFFFL).toInt()
            val left = px * pixelSize
            val top = py * pixelSize
            canvas.drawRect(left, top, left + pixelSize, top + pixelSize, paint)
        }
    }
}
