package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatEditText
import dev1503.oreui.StyleSheet

class OreEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }

    private val P: Float
        get() = styleSheet.pixelSize

    var styleSheet: StyleSheet = StyleSheet.STYLE_EDIT_TEXT
        set(value) {
            field = value
            field.clearCache()
            updateSettings()
            invalidate()
        }

    init {
        background = null
        isCursorVisible = false
        setLineSpacing(0f, 1f)
        includeFontPadding = false
        updateSettings()
    }

    private fun updateSettings() {
        val p = P
        val st = styleSheet.getStyleSheet(StyleSheet.FLAG_DEFAULT)
        val sizeSp = st.textSize ?: 16f
        setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp)

        val shadowHeight = p * 3
        val minH = p * 24

        paint.textSize = textSize
        val fm = paint.fontMetrics
        val fontHeight = fm.descent - fm.ascent

        val totalVerticalSpace = minH - shadowHeight - p
        val verticalPadding = (totalVerticalSpace - fontHeight) / 2f

        val topP = shadowHeight + verticalPadding
        val bottomP = p + verticalPadding

        setPadding((p * 6).toInt(), topP.toInt(), (p * 6).toInt(), bottomP.toInt())
        gravity = Gravity.TOP or Gravity.START
        minHeight = minH.toInt()
    }

    override fun onDraw(canvas: Canvas) {
        val p = P
        val w = width.toFloat()
        val h = height.toFloat()
        val flag = if (isFocused) StyleSheet.FLAG_FOCUSED else StyleSheet.FLAG_DEFAULT
        val st = styleSheet.getStyleSheet(flag)

        paint.color = st.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(0f, 0f, w, h, paint)

        paint.color = st.shadowColor ?: 0
        canvas.drawRect(p, p, w - p, p * 3, paint)

        paint.color = st.backgroundColor ?: 0
        canvas.drawRect(p, p * 3, w - p, h - p, paint)

        setTextColor(st.textColor ?: 0xFFFFFFFF.toInt())
        setHintTextColor((st.textColor ?: 0xFFFFFFFF.toInt()) and 0x80FFFFFF.toInt())

        super.onDraw(canvas)

        if (isFocused && layout != null) {
            val blink = (System.currentTimeMillis() % 1000 < 500)
            if (blink) {
                val pos = selectionStart
                val line = layout.getLineForOffset(pos)
                val x = layout.getPrimaryHorizontal(pos)
                val lineTop = layout.getLineTop(line)
                val lineBottom = layout.getLineBottom(line)

                val cursorW = p * 0.5f
                val cursorH = p * 12f
                val cursorY = lineTop + paddingTop + (lineBottom - lineTop - cursorH) / 2f - scrollY
                val cursorX = x + paddingLeft - scrollX

                if (cursorX >= p && cursorX <= w - p && cursorY >= p * 3 && cursorY <= h - p) {
                    paint.color = st.caretColor ?: st.textColor ?: 0xFFFFFFFF.toInt()
                    canvas.drawRect(cursorX, cursorY, cursorX + cursorW, cursorY + cursorH, paint)
                }
                postInvalidateOnAnimation()
            } else {
                postInvalidateOnAnimation()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val p = P
        val minH = (p * 24).toInt()
        if (measuredHeight < minH) {
            setMeasuredDimension(measuredWidth, minH)
        }
    }
}