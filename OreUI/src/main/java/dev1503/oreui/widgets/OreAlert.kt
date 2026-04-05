package dev1503.oreui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dev1503.oreui.StyleSheet

@SuppressLint("ResourceType")
class OreAlert @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }
    private var manualTextSize = -1f

    private val P: Float
        get() = styleSheet.pixelSize

    private val defaultTextView: TextView = TextView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        gravity = Gravity.CENTER
    }

    var styleSheet: StyleSheet = StyleSheet.STYLE_ALERT_YELLOW
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

        val a = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.textSize, android.R.attr.text))
        if (a.hasValue(0)) {
            manualTextSize = a.getDimension(0, -1f)
        }
        val xmlText = a.getText(1)
        a.recycle()

        if (xmlText != null) text = xmlText
        if (view.parent == null) addView(view)
        updateStyle()
    }

    private fun updateStyle() {
        val s = styleSheet.getStyleSheet(StyleSheet.FLAG_DEFAULT)
        val p = P
        val paddingVal = (p * 2).toInt()

        if (view is TextView) {
            val tv = view as TextView
            tv.setPadding(paddingVal, paddingVal, paddingVal, paddingVal)
            tv.setTextColor(s.textColor ?: 0xFF000000.toInt())
            if (manualTextSize != -1f) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, manualTextSize)
            } else {
                val fontSize = (s.textSize ?: 3f) * p
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
            }
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