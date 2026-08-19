package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import dev1503.oreui.StyleSheet

class OreProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }

    var styleSheet: StyleSheet = StyleSheet.STYLE_PROGRESS_BAR_WHITE
        set(value) {
            field = value
            field.clearCache()
            invalidate()
        }

    var progress: Int = 0
        set(value) {
            val clamped = value.coerceIn(0, max)
            if (clamped != field) {
                field = clamped
                invalidate()
            }
        }

    var max: Int = 100
        set(value) {
            if (value > 0 && value != field) {
                field = value
                progress = progress.coerceIn(0, field)
                invalidate()
            }
        }

    private val P: Float
        get() = styleSheet.pixelSize

    override fun onDraw(canvas: Canvas) {
        val p = P
        val w = width.toFloat()
        val h = height.toFloat()
        val isDisabled = !isEnabled
        val flags = if (isDisabled) StyleSheet.FLAG_DISABLED else StyleSheet.FLAG_DEFAULT
        val st = styleSheet.getStyleSheet(flags)

        paint.color = st.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(0f, 0f, w, h, paint)

        paint.color = st.borderBottomColor ?: st.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(p, p, w - p, h - p, paint)

        paint.color = st.borderTopColor ?: st.borderBottomColor ?: st.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(p, p, w - p, h - p * 2, paint)

        paint.color = st.backgroundColor ?: 0xFF000000.toInt()
        canvas.drawRect(p * 2, p * 2, w - p * 2, h - p * 2, paint)

        val contentMaxWidth = w - 6f * p
        val ratio = if (max > 0) progress.toFloat() / max else 0f
        val contentWidth = contentMaxWidth * ratio

        if (contentWidth > 0f) {
            paint.color = st.textColor ?: 0xFFFFFFFF.toInt()
            canvas.drawRect(3f * p, 3f * p, 3f * p + contentWidth, h - 3f * p, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val p = P
        val defaultHeight = (p * 10).toInt()
        val height = resolveSizeAndState(defaultHeight, heightMeasureSpec, 0)
        val width = resolveSizeAndState((p * 60).toInt(), widthMeasureSpec, 0)
        setMeasuredDimension(width, height)
    }
}
