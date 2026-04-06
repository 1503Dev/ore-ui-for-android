package dev1503.oreui.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatSeekBar
import dev1503.oreui.StyleSheet

open class OreSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.seekBarStyle
) : AppCompatSeekBar(context, attrs, defStyleAttr) {

    private val paint = Paint().apply { isAntiAlias = false }
    private var isHovered = false
    private var visualProgress = 0f
    private var offsetAnimator: ValueAnimator? = null

    private val P: Float
        get() = thumbStyleSheet.pixelSize

    private val THUMB_H_P = 16f
    private val TRACK_H_P = 6f

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
        thumb = null
        progressDrawable = null
        background = null
        setPadding(0, 0, 0, 0)
        visualProgress = progress.toFloat()
    }

    private fun startAnimation(target: Float) {
        offsetAnimator?.cancel()
        offsetAnimator = ValueAnimator.ofFloat(visualProgress, target).apply {
            duration = 150
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                visualProgress = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        val p = P
        val thumbSize = THUMB_H_P * p
        val availableWidth = width - thumbSize
        if (availableWidth <= 0) return super.onTouchEvent(event)

        val touchRatio = ((event.x - thumbSize / 2f) / availableWidth).coerceIn(0f, 1f)
        val targetValue = touchRatio * max

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startAnimation(targetValue)
                progress = targetValue.toInt()
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                offsetAnimator?.cancel()
                visualProgress = targetValue
                progress = targetValue.toInt()
                invalidate()
            }
        }
        return true
    }

    override fun onHoverEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false
        val action = event.action
        if (action == MotionEvent.ACTION_HOVER_ENTER || action == MotionEvent.ACTION_HOVER_EXIT) {
            isHovered = (action == MotionEvent.ACTION_HOVER_ENTER)
            invalidate()
        }
        return super.onHoverEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        val p = P
        val w = width.toFloat()
        val h = height.toFloat()
        val isDisabled = !isEnabled
        val thumbSize = THUMB_H_P * p
        val trackH = TRACK_H_P * p

        val progressRatio = if (max > 0) visualProgress / max else 0f
        val thumbCenterX = progressRatio * (w - thumbSize) + thumbSize / 2f
        val trackCenterY = h / 2f
        val trackTop = trackCenterY - trackH / 2f
        val trackBottom = trackTop + trackH

        val trackFlags = if (isDisabled) StyleSheet.FLAG_DISABLED else StyleSheet.FLAG_DEFAULT

        val sL = trackLeftStyleSheet.getStyleSheet(trackFlags)
        paint.color = sL.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(0f, trackTop, thumbCenterX, trackBottom, paint)
        paint.color = sL.borderBottomColor ?: 0
        canvas.drawRect(p, trackTop + p, thumbCenterX, trackBottom - p, paint)
        paint.color = sL.borderTopColor ?: 0
        canvas.drawRect(p, trackTop + p, thumbCenterX, trackBottom - p * 2, paint)
        paint.color = sL.backgroundColor ?: 0
        canvas.drawRect(p * 2, trackTop + p * 2, thumbCenterX, trackBottom - p * 2, paint)

        val sR = trackRightStyleSheet.getStyleSheet(trackFlags)
        paint.color = sR.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(thumbCenterX, trackTop, w, trackBottom, paint)
        paint.color = sR.borderBottomColor ?: 0
        canvas.drawRect(thumbCenterX, trackTop + p, w - p, trackBottom - p, paint)
        paint.color = sR.borderTopColor ?: 0
        canvas.drawRect(thumbCenterX, trackTop + p, w - p, trackBottom - p * 2, paint)
        paint.color = sR.backgroundColor ?: 0
        canvas.drawRect(thumbCenterX, trackTop + p * 2, w - p * 2, trackBottom - p * 2, paint)

        val tL = thumbCenterX - thumbSize / 2f
        val tR = tL + thumbSize
        val tT = trackCenterY - thumbSize / 2f
        val tB = tT + thumbSize

        val thumbFlags = if (isDisabled) StyleSheet.FLAG_DISABLED else (if (isHovered) StyleSheet.FLAG_HOVERED else StyleSheet.FLAG_DEFAULT)
        val st = thumbStyleSheet.getStyleSheet(thumbFlags)

        paint.color = st.outlineColor ?: 0xFF1E1E1F.toInt()
        canvas.drawRect(tL, tT, tR, tB, paint)
        paint.color = st.shadowColor ?: 0
        canvas.drawRect(tL + p, tB - p * 3, tR - p, tB - p, paint)
        paint.color = st.borderBottomColor ?: 0
        canvas.drawRect(tL + p, tT + p, tR - p, tB - p * 3, paint)
        paint.color = st.borderTopColor ?: 0
        canvas.drawRect(tL + p, tT + p, tR - p * 2, tB - p * 4, paint)
        paint.color = st.backgroundColor ?: 0
        canvas.drawRect(tL + p * 2, tT + p * 2, tR - p * 2, tB - p * 4, paint)
    }

    override fun setProgress(progress: Int) {
        super.setProgress(progress)
        if (offsetAnimator?.isRunning != true) {
            visualProgress = progress.toFloat()
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val p = P
        setMeasuredDimension(resolveSize((p * 100).toInt(), widthMeasureSpec), (p * THUMB_H_P).toInt())
    }
}