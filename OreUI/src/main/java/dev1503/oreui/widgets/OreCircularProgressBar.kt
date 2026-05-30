package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import dev1503.oreui.StyleSheet
import dev1503.oreui.Pixels2D

class OreCircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val FRAME_DURATION = 33L
        private val FRAME_DURATIONS = intArrayOf(4, 4, 5, 4, 4, 3, 5, 3, 4, 5)
    }

    private val paint = Paint().apply { isAntiAlias = false }

    var styleSheet: StyleSheet = StyleSheet.STYLE_PROGRESS_BAR_WHITE
        set(value) {
            field = value
            field.clearCache()
            invalidate()
        }

    private val P: Float
        get() = styleSheet.pixelSize

    private var currentImageIndex = 0
    private var currentFrameCount = 0
    private val animationHandler = Handler(Looper.getMainLooper())

    private val animationRunnable = object : Runnable {
        override fun run() {
            currentFrameCount++
            if (currentFrameCount >= FRAME_DURATIONS[currentImageIndex]) {
                currentFrameCount = 0
                currentImageIndex = (currentImageIndex + 1) % Pixels2D.PIXELS_CIRCULAR_PROGRESSES.size
            }
            invalidate()
            animationHandler.postDelayed(this, FRAME_DURATION)
        }
    }

    init {
        animationHandler.post(animationRunnable)
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
        val isDisabled = !isEnabled
        val thumbFlags = if (isDisabled) StyleSheet.FLAG_DISABLED else StyleSheet.FLAG_DEFAULT
        val st = styleSheet.getStyleSheet(thumbFlags)

        val pixels2d = Pixels2D.PIXELS_CIRCULAR_PROGRESSES.getOrNull(currentImageIndex) ?: return
        paint.color = st.textColor ?: 0xFFFFFFFF.toInt()

        val startX = (width - pixels2d.width * p) / 2f
        val startY = (height - pixels2d.height * p) / 2f
        drawPixels(canvas, pixels2d, startX, startY, p)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val p = P
        val size = (p * 7).toInt()
        val width = resolveSizeAndState(size, widthMeasureSpec, 0)
        val height = resolveSizeAndState(size, heightMeasureSpec, 0)
        setMeasuredDimension(width, height)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animationHandler.removeCallbacks(animationRunnable)
        animationHandler.post(animationRunnable)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animationHandler.removeCallbacks(animationRunnable)
    }
}