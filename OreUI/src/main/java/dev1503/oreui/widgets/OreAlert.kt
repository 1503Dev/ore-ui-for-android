package dev1503.oreui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dev1503.oreui.StyleSheet
import androidx.core.content.withStyledAttributes
import androidx.core.content.res.ResourcesCompat

@SuppressLint("ResourceType")
open class OreAlert @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }
    private var manualTextSize = -1f
    private var manualTypeface: Typeface? = null

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
                if (field.parent == this) removeView(field)
                field = value
                if (field.parent == null) addView(field)
                updateStyle()
            }
        }

    var text: CharSequence
        get() = (view as? TextView)?.text ?: ""
        set(value) {
            (view as? TextView)?.text = value
        }

    init {
        setWillNotDraw(false)
        orientation = VERTICAL
        gravity = Gravity.CENTER

        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(
            android.R.attr.textSize,     // 0
            android.R.attr.text,         // 1
            android.R.attr.fontFamily,   // 2
            android.R.attr.typeface,     // 3
            android.R.attr.textStyle     // 4
        ))

        if (typedArray.hasValue(0)) {
            manualTextSize = typedArray.getDimension(0, -1f)
        }
        if (typedArray.hasValue(1)) {
            text = typedArray.getText(1) ?: ""
        }

        val fontFamilyId = typedArray.getResourceId(2, 0)
        if (fontFamilyId != 0) {
            manualTypeface = ResourcesCompat.getFont(context, fontFamilyId)
        }

        if (manualTypeface == null && typedArray.hasValue(3)) {
            val tfIndex = typedArray.getInt(3, -1)
            val styleIndex = typedArray.getInt(4, Typeface.NORMAL)
            manualTypeface = when (tfIndex) {
                1 -> Typeface.SANS_SERIF
                2 -> Typeface.SERIF
                3 -> Typeface.MONOSPACE
                else -> null
            }
            if (manualTypeface != null && styleIndex != Typeface.NORMAL) {
                manualTypeface = Typeface.create(manualTypeface, styleIndex)
            }
        }

        typedArray.recycle()

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

            val targetTypeface = s.typeface ?: manualTypeface
            if (tv.typeface != targetTypeface) {
                tv.typeface = targetTypeface
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