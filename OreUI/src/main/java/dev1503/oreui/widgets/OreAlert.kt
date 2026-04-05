package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dev1503.oreui.StyleSheet

class OreAlert @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        val STYLE_ALERT_YELLOW = StyleSheet().apply {
            backgroundColor = 0xFFEFE866.toInt()
            textColor = 0xFF000000.toInt()
        }
    }

    private val paint = Paint().apply { isAntiAlias = false }
    private val P = 6f

    private val defaultTextView: TextView = TextView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val p = (P * 2).toInt()
        setPadding(p, p, p, p)
        textSize = 14f
        gravity = Gravity.CENTER
    }

    var styleSheet: StyleSheet = STYLE_ALERT_YELLOW
        set(value) {
            field = value
            updateStyle()
        }

    var view: View = defaultTextView
        set(value) {
            if (field != value) {
                removeView(field)
                field = value
                addView(field)
                updateStyle()
            }
        }

    var text: CharSequence
        get() = defaultTextView.text
        set(value) {
            defaultTextView.text = value
        }

    init {
        setWillNotDraw(false)
        orientation = VERTICAL
        gravity = Gravity.CENTER

        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.text))
        val xmlText = typedArray.getText(0)
        typedArray.recycle()

        if (xmlText != null) {
            text = xmlText
        }

        if (view.parent == null) {
            addView(view)
        }
        updateStyle()
    }

    private fun updateStyle() {
        val s = styleSheet.getStyleSheet(StyleSheet.FLAG_DEFAULT)
        if (view is TextView) {
            (view as TextView).setTextColor(s.textColor ?: 0xFF000000.toInt())
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val s = styleSheet.getStyleSheet(StyleSheet.FLAG_DEFAULT)
        paint.color = s.backgroundColor ?: 0xFFEFE866.toInt()
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        super.onDraw(canvas)
    }
}