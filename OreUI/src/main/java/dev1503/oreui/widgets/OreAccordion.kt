package dev1503.oreui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import dev1503.oreui.StyleSheet

open class OreAccordion @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : OrePanel(context, attrs, defStyleAttr) {

    private var headerCard: OreCard? = null
    private val container: LinearLayout = LinearLayout(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setPadding(styleSheet.calcPixelSize().toInt())
        visibility = GONE
    }
    private var headerCardTitleTextView: TextView? = null
    private var headerCardSubtitleTextView: TextView? = null

    private var _title: CharSequence? = null
    var title: CharSequence?
        get() = _title
        set(value) {
            _title = value
            if (headerCardTitleTextView == null) {
                headerCardSubtitleTextView?.let { headerCard?.removeView(it) }
                headerCardTitleTextView = TextView(context).apply {
                    layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                        setMargins(0, 0, styleSheet.calcPixelSize(8f).toInt(), 0)
                    }
                    if (styleSheet.textSize != null) {
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, styleSheet.calcPixelSize(styleSheet.textSize!!))
                    }
                    if (styleSheet.typeface != null) {
                        typeface = styleSheet.typeface
                    }
                    if (styleSheet.textColor != null) {
                        setTextColor(styleSheet.textColor!!)
                    }
                }
                headerCard?.addView(headerCardTitleTextView)
                headerCardSubtitleTextView?.let { headerCard?.addView(it) }
            }
            headerCardTitleTextView?.text = value
        }
    private var _subtitle: CharSequence? = null
    var subtitle: CharSequence?
        get() = _subtitle
        set(value) {
            _subtitle = value
            if (headerCardSubtitleTextView == null) {
                headerCardSubtitleTextView = TextView(context).apply {
                    if (styleSheet.textSize != null) {
                        setTextSize(TypedValue.COMPLEX_UNIT_PX, styleSheet.calcPixelSize(styleSheet.textSize!!))
                    }
                    if (styleSheet.typeface != null) {
                        typeface = styleSheet.typeface
                    }
                    if (styleSheet.textColor != null) {
                        setTextColor(styleSheet.textColor!!)
                    }
                    alpha = 0.5f
                }
                headerCard?.addView(headerCardSubtitleTextView)
            }
            headerCardSubtitleTextView?.text = value
        }

    var isExpanded: Boolean = false
        set(value) {
            field = value
            Log.d("OreAccordion", "isExpanded: $value")
            container.visibility = if (value) VISIBLE else GONE
            updateHeaderArrow()
        }

    var contentView: View? = null
        set(value) {
            if (field != value) {
                container.removeAllViews()
                container.addView(value)
            }
        }

    init {
        headerCard = OreCard(context).apply {
            styleSheet = StyleSheet.STYLE_CARD_DARK
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
            orientation = LinearLayout.HORIZONTAL
            setOnClickListener { toggle() }
        }
        orientation = VERTICAL
        outlineEnabled = true
        borderEnabled = false

        super.addView(headerCard, 0)
        super.addView(container, 1)
    }

    fun toggle() {
        Thread.currentThread().stackTrace.forEach {
            println("${it.className}.${it.methodName}(${it.fileName}:${it.lineNumber})")
        }
        isExpanded = !isExpanded
    }

    private fun updateHeaderArrow() {
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {}
}