package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.SwitchCompat
import dev1503.oreui.StyleSheet
import dev1503.oreui.Pixels2D

class OreSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.switchStyle
) : SwitchCompat(context, attrs, defStyleAttr) {

    companion object {
        const val ANIMATION_ENABLED = false
    }

    private val paint = Paint().apply { isAntiAlias = false }
    private var isHovered = false

    private var thumbIcon: Pixels2D? = null

    private val P: Float
        get() = thumbStyleSheet.pixelSize

    private val TRACK_TOP_GAP: Float
        get() = P * 2

    var thumbStyleSheet: StyleSheet = StyleSheet.STYLE_WHITE
        set(value) {
            field = value
            field.clearCache()
            invalidate()
        }

    var trackLeftStyleSheet: StyleSheet = StyleSheet.STYLE_GREEN
        set(value) {
            field = value
            field.clearCache()
            invalidate()
        }

    var trackRightStyleSheet: StyleSheet = StyleSheet.STYLE_INACTIVATED
        set(value) {
            field = value
            field.clearCache()
            invalidate()
        }

    init {
        thumbDrawable = null
        trackDrawable = null
        background = null
    }

    fun setThumbIcon(pixels: Pixels2D?) {
        this.thumbIcon = pixels
        invalidate()
    }

    override fun onHoverEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false
        when (event.action) {
            MotionEvent.ACTION_HOVER_ENTER -> {
                isHovered = true
                invalidate()
            }
            MotionEvent.ACTION_HOVER_EXIT -> {
                isHovered = false
                invalidate()
            }
        }
        return super.onHoverEvent(event)
    }

    private fun drawPixels(canvas: Canvas, pixels2D: Pixels2D, startX: Float, startY: Float, pixelSize: Float) {
        pixels2D.pixels.forEach { packed ->
            val px = (packed shr 32).toInt()
            val py = (packed and 0xFFFFFFFFL).toInt()
            val left = startX + px * pixelSize
            val top = startY + py * pixelSize
            canvas.drawRect(left, top, left + pixelSize, top + pixelSize, paint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val p = P
        val w = width.toFloat()
        val h = height.toFloat()

        val isDisabled = !isEnabled
        val thumbSize = h
        val progress = if (ANIMATION_ENABLED) thumbPosition else (if (isChecked) 1f else 0f)
        val thumbLeft = progress * (w - thumbSize)
        val thumbRight = thumbLeft + thumbSize

        val trackLeftFlags = if (isDisabled) StyleSheet.FLAG_DISABLED else StyleSheet.FLAG_DEFAULT
        val trackRightFlags = if (isDisabled) StyleSheet.FLAG_DISABLED else StyleSheet.FLAG_DEFAULT

        val sL = trackLeftStyleSheet.getStyleSheet(trackLeftFlags)
        val sR = trackRightStyleSheet.getStyleSheet(trackRightFlags)

        paint.color = sL.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(0f, TRACK_TOP_GAP, w, h, paint)

        val mid = w / 2f

        paint.color = sL.borderBottomColor ?: 0
        canvas.drawRect(p, TRACK_TOP_GAP + p, mid, h - p, paint)
        paint.color = sL.borderTopColor ?: 0
        canvas.drawRect(p, TRACK_TOP_GAP + p, mid, h - p * 2, paint)
        paint.color = sL.backgroundColor ?: 0
        canvas.drawRect(p * 2, TRACK_TOP_GAP + p * 2, mid, h - p * 2, paint)

        paint.color = sR.borderBottomColor ?: 0
        canvas.drawRect(mid, TRACK_TOP_GAP + p, w - p, h - p, paint)
        paint.color = sR.borderTopColor ?: 0
        canvas.drawRect(mid, TRACK_TOP_GAP + p, w - p, h - p * 2, paint)
        paint.color = sR.backgroundColor ?: 0
        canvas.drawRect(mid, TRACK_TOP_GAP + p * 2, w - p * 2, h - p * 2, paint)

        val centerY = TRACK_TOP_GAP + (h - TRACK_TOP_GAP) / 2f

        paint.color = sL.textColor ?: 0xFFFFFFFF.toInt()
        val leftIcon = Pixels2D.PIXELS_SWITCH_LEFT
        val leftIconX = mid / 2f - (leftIcon.width * p) / 2f
        val leftIconY = centerY - (leftIcon.height * p) / 2f
        drawPixels(canvas, leftIcon, leftIconX, leftIconY, p)

        paint.color = sR.textColor ?: 0xFF1E1E1F.toInt()
        val rightIcon = Pixels2D.PIXELS_SWITCH_RIGHT
        val rightIconX = mid + (mid / 2f) - (rightIcon.width * p) / 2f - p
        val rightIconY = centerY - (rightIcon.height * p) / 2f
        drawPixels(canvas, rightIcon, rightIconX, rightIconY, p)

        val thumbFlags = if (isDisabled) StyleSheet.FLAG_DISABLED else (if (isHovered) StyleSheet.FLAG_HOVERED else StyleSheet.FLAG_DEFAULT)
        val st = thumbStyleSheet.getStyleSheet(thumbFlags)

        paint.color = st.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(thumbLeft, 0f, thumbRight, h, paint)

        paint.color = st.shadowColor ?: 0
        canvas.drawRect(thumbLeft + p, h - p * 3, thumbRight - p, h - p, paint)

        paint.color = st.borderBottomColor ?: 0
        canvas.drawRect(thumbLeft + p, p, thumbRight - p, h - p * 3, paint)

        paint.color = st.borderTopColor ?: 0
        canvas.drawRect(thumbLeft + p, p, thumbRight - p * 2, h - p * 4, paint)

        paint.color = st.backgroundColor ?: 0
        canvas.drawRect(thumbLeft + p * 2, p * 2, thumbRight - p * 2, h - p * 4, paint)

        thumbIcon?.let { icon ->
            paint.color = st.textColor ?: 0xFF000000.toInt()
            val iconX = thumbLeft + (thumbSize / 2f) - (icon.width * p) / 2f
            val iconY = (h / 2f) - (icon.height * p) / 2f - p
            drawPixels(canvas, icon, iconX, iconY, p)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val p = P
        val h = (p * 16).toInt()
        val w = (p * 30).toInt()
        setMeasuredDimension(w, h)
    }
}