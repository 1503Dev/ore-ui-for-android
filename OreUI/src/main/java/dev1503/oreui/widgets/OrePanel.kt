package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import dev1503.oreui.StyleSheet

class OrePanel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }

    private val P: Float
        get() = styleSheet.pixelSize

    var styleSheet: StyleSheet = StyleSheet.STYLE_PANEL
        set(value) {
            field = value
            field.clearCache()
            updatePadding()
            invalidate()
        }

    var borderEnabled: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    var sideBorderEnabled: Boolean = true
        set(value) {
            field = value
            invalidate()
        }

    var outlineEnabled: Boolean = true
        set(value) {
            field = value
            invalidate()
        }

    init {
        setWillNotDraw(false)
        updatePadding()
    }

    private fun updatePadding() {
        val p = P
        val horizontalPadding = (p * 9).toInt()
        val verticalPadding = (p * 7.5).toInt()
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val p = P
        val w = width.toFloat()
        val h = height.toFloat()

        val flag = if (isEnabled) StyleSheet.FLAG_DEFAULT else StyleSheet.FLAG_DISABLED
        val s = styleSheet.getStyleSheet(flag)

        val startPos = if (outlineEnabled) {
            paint.color = s.outlineColor ?: 0xFF101419.toInt()
            canvas.drawRect(0f, 0f, w, h, paint)
            p
        } else {
            0f
        }

        if (borderEnabled) {
            val left = if (sideBorderEnabled) startPos else 0f
            val right = if (sideBorderEnabled) w - startPos else w

            paint.color = s.borderBottomColor ?: 0
            canvas.drawRect(left, startPos, right, h - startPos, paint)

            paint.color = s.borderTopColor ?: 0
            val btopRight = if (sideBorderEnabled) right - p else w
            canvas.drawRect(left, startPos, btopRight, h - startPos - p, paint)

            paint.color = s.backgroundColor ?: 0xFFFFFFFF.toInt()
            val contentLeft = if (sideBorderEnabled) left + p else 0f
            val contentRight = if (sideBorderEnabled) right - p else w
            canvas.drawRect(contentLeft, startPos + p, contentRight, h - startPos - p, paint)
        } else {
            val left = if (outlineEnabled) p else 0f
            val right = if (outlineEnabled) w - p else w
            paint.color = s.backgroundColor ?: 0xFFFFFFFF.toInt()
            canvas.drawRect(left, startPos, right, h - startPos, paint)
        }

        super.onDraw(canvas)
    }
}