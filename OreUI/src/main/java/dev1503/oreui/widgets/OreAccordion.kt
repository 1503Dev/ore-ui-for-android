package dev1503.oreui.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import dev1503.oreui.Pixels2D
import dev1503.oreui.StyleSheet

open class OreAccordion @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : OrePanel(context, attrs, defStyleAttr) {

    var headerCard: OreCard? = null
    private val container: LinearLayout = LinearLayout(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        visibility = GONE
        clipChildren = true
    }
    private var headerCardTitleTextView: TextView? = null
    private var headerCardSubtitleTextView: TextView? = null
    private var headerCardArrow: PixelsIcon? = null
    private val spacer: View = View(context).apply {
        layoutParams = LayoutParams(0, 0, 1f)
    }

    private var _title: CharSequence? = null
    var title: CharSequence?
        get() = _title
        set(value) {
            _title = value
            if (headerCardTitleTextView == null) {
                headerCardTitleTextView = TextView(context).apply {
                    layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                        setMargins(0, 0, styleSheet.calcPixelSize(8f).toInt(), 0)
                    }
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, styleSheet.calcTextSize())
                    typeface = styleSheet.typeface
                    setTextColor(styleSheet.textColor ?: 0xFFFFFFFF.toInt())
                }
                updateHeaderLayout()
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
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, styleSheet.calcTextSize())
                    typeface = styleSheet.typeface
                    setTextColor(styleSheet.textColor ?: 0xFFFFFFFF.toInt())
                    alpha = 0.5f
                    layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                }
                updateHeaderLayout()
            }
            headerCardSubtitleTextView?.text = value
        }

    var isExpanded: Boolean = false
        set(value) {
            if (field == value) return
            field = value
            if (value) {
                expandWithAnimation()
            } else {
                container.visibility = GONE
            }
            updateHeaderArrow()
        }

    var contentView: View? = null
        set(value) {
            if (field != value) {
                container.removeAllViews()
                value?.let { container.addView(it) }
                field = value
            }
        }

    init {
        orientation = VERTICAL
        outlineEnabled = true
        borderEnabled = false

        headerCard = OreCard(context).apply {
            styleSheet = StyleSheet.STYLE_CARD_DARK
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
            orientation = HORIZONTAL
            setOnClickListener { toggle() }

            headerCardArrow = PixelsIcon(context).apply {
                pixelSize = styleSheet.pixelSize
                layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    setMargins(styleSheet.calcPixelSize(2f).toInt(), 0, styleSheet.calcPixelSize(2f).toInt(), 0)
                }
            }
        }

        container.setPadding(styleSheet.pixelSize.toInt())

        super.addView(headerCard, 0)
        super.addView(container, 1)

        updateHeaderLayout()
        updateHeaderArrow()
    }

    private fun expandWithAnimation() {
        container.measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        )
        val targetHeight = container.measuredHeight

        container.layoutParams.height = 0
        container.visibility = VISIBLE

        ValueAnimator.ofInt(0, targetHeight).apply {
            duration = 50
            interpolator = DecelerateInterpolator()
            addUpdateListener { animator ->
                container.layoutParams.height = animator.animatedValue as Int
                container.requestLayout()
            }
            addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    container.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
            })
            start()
        }
    }

    private fun updateHeaderLayout() {
        headerCard?.let { card ->
            card.removeAllViews()
            headerCardTitleTextView?.let { card.addView(it) }
            headerCardSubtitleTextView?.let { card.addView(it) }
            card.addView(spacer)
            headerCardArrow?.let { card.addView(it) }
        }
    }

    fun toggle() {
        isExpanded = !isExpanded
    }

    private fun updateHeaderArrow() {
        headerCardArrow?.let { arrow ->
            arrow.pixels2d = if (!isExpanded) Pixels2D.PIXELS_SHORT_ARROW_DOWN else Pixels2D.PIXELS_SHORT_ARROW_UP
            arrow.invalidate()
            arrow.requestLayout()
        }
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {}
}