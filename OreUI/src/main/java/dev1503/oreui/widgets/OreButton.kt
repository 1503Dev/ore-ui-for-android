package dev1503.oreui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import dev1503.oreui.StyleSheet
import androidx.core.graphics.withTranslation
import dev1503.oreui.events.OnUnhoverListener
import androidx.core.content.withStyledAttributes
import dev1503.oreui.Pixels2D

@SuppressLint("ResourceType")
open class OreButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }
    private var P: Float = 0f
    private var PRESS_OFFSET: Float = 0f
    private var SIDE_PADDING: Float = 0f
    private var currentFlags = StyleSheet.FLAG_DEFAULT
    private var manualTextSize = -1f
    private var manualTypeface: Typeface? = null

    private var onHoverListeners: MutableList<dev1503.oreui.events.OnHoverListener>? = null
    private var onUnhoverListeners: MutableList<OnUnhoverListener>? = null

    var pixels2d: Pixels2D? = null
        set(value) {
            field = value
            updatePadding()
            requestLayout()
            invalidate()
        }

    var styleSheet: StyleSheet = StyleSheet.STYLE_WHITE
        set(value) {
            field = value
            field.clearCache()
            updatePixelConstants()
            updateState()
        }

    init {
        background = null
        isAllCaps = false
        minWidth = 0
        minHeight = 0
        gravity = android.view.Gravity.CENTER

        context.withStyledAttributes(
            attrs,
            intArrayOf(android.R.attr.textSize, android.R.attr.fontFamily, android.R.attr.typeface)
        ) {
            if (hasValue(0)) {
                manualTextSize = getDimension(0, -1f)
            }
            if (hasValue(1) || hasValue(2)) {
                manualTypeface = typeface
            }
        }

        updatePixelConstants()
        updateState()
    }

    override fun setTextSize(unit: Int, size: Float) {
        super.setTextSize(unit, size)
        if (unit != -100) {
            manualTextSize = TypedValue.applyDimension(unit, size, resources.displayMetrics)
        }
    }

    override fun setTypeface(tf: Typeface?) {
        super.setTypeface(tf)
        manualTypeface = tf
    }

    private fun updatePixelConstants() {
        val ps = styleSheet.pixelSize
        P = ps
        PRESS_OFFSET = ps * 2
        SIDE_PADDING = ps * 4
        updatePadding()
    }

    private fun updatePadding() {
        if (text.isEmpty() && pixels2d != null && pixels2d!!.width == pixels2d!!.height) {
            setPadding(0, 0, 0, 0)
        } else {
            val iconSpace = if (pixels2d != null && text.isNotEmpty()) {
                (pixels2d!!.width * P) + (4 * P)
            } else 0f
            setPadding((SIDE_PADDING + iconSpace).toInt(), 0, SIDE_PADDING.toInt(), 0)
        }
    }

    fun setIconPixels2D(pixels: Pixels2D?) {
        this.pixels2d = pixels
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

    private fun updateState() {
        val tempFlags = if (!isEnabled) (StyleSheet.FLAG_DISABLED) else currentFlags
        val s = styleSheet.getStyleSheet(tempFlags)

        if (manualTextSize == -1f) {
            val fontSize = s.calcTextSize()
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize)
        }

        val targetTypeface = s.typeface ?: manualTypeface
        if (super.getTypeface() != targetTypeface) {
            super.setTypeface(targetTypeface)
        }

        setTextColor(s.textColor ?: 0xFFFFFFFF.toInt())
        invalidate()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (!enabled) {
            removeFlag(StyleSheet.FLAG_PRESSED or StyleSheet.FLAG_HOVERED)
        }
        updateState()
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        updatePadding()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val ps = styleSheet.pixelSize
        val defaultHeight = (ps * 24).toInt()

        val hSpec = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> heightMeasureSpec
            MeasureSpec.AT_MOST -> MeasureSpec.makeMeasureSpec(minOf(defaultHeight, MeasureSpec.getSize(heightMeasureSpec)), MeasureSpec.EXACTLY)
            else -> MeasureSpec.makeMeasureSpec(defaultHeight, MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, hSpec)

        var targetWidth = measuredWidth
        val targetHeight = measuredHeight

        val p2d = pixels2d
        if (text.isEmpty() && p2d != null) {
            if (p2d.width == p2d.height) {
                targetWidth = targetHeight
            } else {
                val iconW = p2d.width * P
                val iconH = p2d.height * P
                val verticalMargin = (targetHeight - iconH) / 2f
                targetWidth = (iconW + verticalMargin * 2).toInt()
            }
        }

        setMeasuredDimension(resolveSize(targetWidth, widthMeasureSpec), targetHeight)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false
        val isInside = event.x >= 0 && event.x < width && event.y >= 0 && event.y < height
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isPressed = true
                addFlag(StyleSheet.FLAG_PRESSED or StyleSheet.FLAG_HOVERED)
            }
            MotionEvent.ACTION_MOVE -> {
                isPressed = isInside
                if (isInside) addFlag(StyleSheet.FLAG_PRESSED or StyleSheet.FLAG_HOVERED)
                else removeFlag(StyleSheet.FLAG_PRESSED or StyleSheet.FLAG_HOVERED)
            }
            MotionEvent.ACTION_UP -> {
                if (isInside) performClick()
                isPressed = false
                removeFlag(StyleSheet.FLAG_PRESSED)
                if (isInside && event.getToolType(0) != MotionEvent.TOOL_TYPE_FINGER) addFlag(StyleSheet.FLAG_HOVERED)
                else removeFlag(StyleSheet.FLAG_HOVERED)
            }
            MotionEvent.ACTION_CANCEL -> {
                isPressed = false
                removeFlag(StyleSheet.FLAG_PRESSED or StyleSheet.FLAG_HOVERED)
            }
        }
        return true
    }

    override fun onHoverEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false
        when (event.action) {
            MotionEvent.ACTION_HOVER_ENTER -> {
                onHoverListeners?.forEach { it.onHover(this, event) }
                addFlag(StyleSheet.FLAG_HOVERED)
            }
            MotionEvent.ACTION_HOVER_EXIT -> {
                onUnhoverListeners?.forEach { it.onUnhover(this, event) }
                removeFlag(StyleSheet.FLAG_HOVERED)
            }
        }
        return super.onHoverEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()

        val isDisabled = !isEnabled
        val isActive = (currentFlags and StyleSheet.FLAG_ACTIVE) != 0

        val colorFlags = if (isDisabled) StyleSheet.FLAG_DISABLED else (if (isActive) StyleSheet.FLAG_ACTIVE else currentFlags)
        val s = styleSheet.getStyleSheet(colorFlags)

        val isPressedLook = ((currentFlags and StyleSheet.FLAG_PRESSED) != 0 || isActive)
        val drawOffset = if (isPressedLook) PRESS_OFFSET else 0f

        if (isPressedLook) {
            val off = PRESS_OFFSET
            paint.color = s.outlineColor ?: 0xFF1E1E1F.toInt()
            canvas.drawRect(0f, off, w, h, paint)
            paint.color = s.borderBottomColor ?: 0
            canvas.drawRect(P, off + P, w - P, h - P, paint)
            paint.color = s.borderTopColor ?: 0
            canvas.drawRect(P, off + P, w - P * 2, h - P * 2, paint)
            paint.color = s.backgroundColor ?: 0
            canvas.drawRect(P * 2, off + P * 2, w - P * 2, h - P * 2, paint)

            if (isActive) {
                val barW = P * 24
                val barH = P * 1
                val barX = (w - barW) / 2f
                val barY = h - P - barH
                paint.color = s.textColor ?: 0xFFFFFFFF.toInt()
                canvas.drawRect(barX, barY, barX + barW, barY + barH, paint)
            }
        } else {
            paint.color = s.outlineColor ?: 0xFF101419.toInt()
            canvas.drawRect(0f, 0f, w, h, paint)
            paint.color = s.shadowColor ?: 0
            canvas.drawRect(P, h - P * 3, w - P, h - P, paint)
            paint.color = s.borderBottomColor ?: 0
            canvas.drawRect(P, P, w - P, h - P * 3, paint)
            paint.color = s.borderTopColor ?: 0
            canvas.drawRect(P, P, w - P * 2, h - P * 4, paint)
            paint.color = s.backgroundColor ?: 0
            canvas.drawRect(P * 2, P * 2, w - P * 2, h - P * 4, paint)
        }

        pixels2d?.let { p2d ->
            paint.color = s.textColor ?: 0xFFFFFFFF.toInt()
            val iconW = p2d.width * P
            val iconH = p2d.height * P
            val spacing = 4 * P

            val iconX: Float
            val iconY = (h - iconH) / 2f

            if (text.isNotEmpty()) {
                val textPaint = getPaint()
                val textWidth = textPaint.measureText(text.toString())
                val totalContentWidth = iconW + spacing + textWidth
                iconX = (w - totalContentWidth) / 2f
            } else {
                iconX = (w - iconW) / 2f
            }

            val visualY = iconY + if (isPressedLook) drawOffset / 2f else -P

            canvas.withTranslation(iconX, visualY) {
                p2d.pixels.forEach { pixel ->
                    val px = (pixel shr 32).toFloat() * P
                    val py = (pixel and 0xFFFFFFFFL).toFloat() * P
                    drawRect(px, py, px + P, py + P, paint)
                }
            }
        }

        if (isPressedLook) {
            canvas.withTranslation(0f, drawOffset / 2f) { super.onDraw(this) }
        } else {
            canvas.withTranslation(0f, -P) { super.onDraw(this) }
        }
    }
}