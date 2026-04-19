package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import dev1503.oreui.StyleSheet
import androidx.core.graphics.withClip

open class HeaderLabel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : OreTextView(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }

    init {
        styleSheet = StyleSheet.STYLE_PURPLE
        includeFontPadding = false
        gravity = android.view.Gravity.CENTER_VERTICAL
        val p = styleSheet.pixelSize
        setPadding((p * 4).toInt(), 0, (p * 4).toInt(), 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val p = styleSheet.pixelSize
        val labelHeight = (p * 15).toInt()
        val hSpec = MeasureSpec.makeMeasureSpec(labelHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, hSpec)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(parentWidth, labelHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val p = styleSheet.pixelSize
        val s = styleSheet.getStyleSheet(0)

        val textWidth = getPaint().measureText(text.toString())
        val labelWidth = textWidth + paddingLeft + paddingRight

        val hTotal = height.toFloat()
        val hBar = p * 5
        val viewW = width.toFloat()
        val outline = p

        paint.color = s.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(0f, 0f, labelWidth, hTotal, paint)
        canvas.drawRect(labelWidth, hTotal - hBar, viewW, hTotal, paint)

        paint.color = s.backgroundColor ?: 0
        canvas.drawRect(outline, outline, labelWidth - outline, hTotal - outline, paint)
        canvas.drawRect(labelWidth - outline, hTotal - hBar + outline, viewW - outline, hTotal - outline, paint)

        paint.color = s.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(labelWidth - outline, 0f, labelWidth, hTotal - hBar + outline, paint)

        canvas.withClip(outline, outline, labelWidth - outline, hTotal - outline) {
            super.onDraw(this)
        }
    }
}