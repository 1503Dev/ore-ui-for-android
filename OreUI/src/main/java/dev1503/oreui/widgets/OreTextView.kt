package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import dev1503.oreui.StyleSheet
import androidx.core.content.withStyledAttributes

open class OreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var currentFlags = StyleSheet.FLAG_DEFAULT
    private var isUpdatingState = false

    var styleSheet: StyleSheet = StyleSheet.STYLE_TEXT_VIEW
        set(value) {
            field = value
            field.clearCache()
            updateState()
        }

    init {
        context.withStyledAttributes(attrs, intArrayOf(android.R.attr.textSize)) {
            if (hasValue(0)) {
                val xmlSize = getDimension(0, 0f)
                styleSheet.textSize = xmlSize / styleSheet.pixelSize
            }
        }
        updateState()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateState()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
//        if (!isUpdatingState) {
//            val pxSize = TypedValue.applyDimension(
//                unit, size, context.resources.displayMetrics
//            )
//            styleSheet.textSize = pxSize / styleSheet.pixelSize
//        }
    }

    private fun updateState() {
        isUpdatingState = true
        val tempFlags = if (!isEnabled) StyleSheet.FLAG_DISABLED else currentFlags
        val s = styleSheet.getStyleSheet(tempFlags)

        val fontSize = s.calcPixelSize(s.textSize ?: 7.5f)
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)

        val targetTypeface = s.typeface
        if (typeface != targetTypeface) {
            typeface = targetTypeface
        }

        setTextColor(s.textColor ?: 0xFFFFFFFF.toInt())
        isUpdatingState = false
        invalidate()
    }

    fun addFlag(flag: Int) {
        val next = currentFlags or flag
        if (next != currentFlags) {
            currentFlags = next
            updateState()
        }
    }

    fun removeFlag(flag: Int) {
        val next = currentFlags and flag.inv()
        if (next != currentFlags) {
            currentFlags = next
            updateState()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val tempFlags = if (!isEnabled) StyleSheet.FLAG_DISABLED else currentFlags
        val s = styleSheet.getStyleSheet(tempFlags)

        s.backgroundColor?.let {
            canvas.drawColor(it)
        }

        super.onDraw(canvas)
    }
}