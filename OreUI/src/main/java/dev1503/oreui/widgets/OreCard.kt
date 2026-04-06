package dev1503.oreui.widgets

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import dev1503.oreui.StyleSheet
import dev1503.oreui.events.OnHoverListener as OnHoverListener2
import dev1503.oreui.events.OnUnhoverListener

open class OreCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : OrePanel(context, attrs, defStyleAttr) {

    private var currentFlags = StyleSheet.FLAG_DEFAULT
    private var onHoverListeners: MutableList<OnHoverListener2>? = null
    private var onUnhoverListeners: MutableList<OnUnhoverListener>? = null
    private var onClickListener: OnClickListener? = null

    init {
        isClickable = true
        isFocusable = true
        borderEnabled = true
    }

    override fun getCurrentFlags(): Int {
        return if (!isEnabled) StyleSheet.FLAG_DISABLED else currentFlags
    }

    fun addOnHoverListener(listener: OnHoverListener2) {
        if (onHoverListeners == null) onHoverListeners = mutableListOf()
        onHoverListeners?.add(listener)
    }

    fun removeOnHoverListener(listener: OnHoverListener2) {
        onHoverListeners?.remove(listener)
    }

    fun addOnUnhoverListener(listener: OnUnhoverListener) {
        if (onUnhoverListeners == null) onUnhoverListeners = mutableListOf()
        onUnhoverListeners?.add(listener)
    }

    fun removeOnUnhoverListener(listener: OnUnhoverListener) {
        onUnhoverListeners?.remove(listener)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        this.onClickListener = l
    }

    private fun addFlag(flag: Int) {
        val next = currentFlags or flag
        if (next != currentFlags) {
            currentFlags = next
            invalidate()
        }
    }

    private fun removeFlag(flag: Int) {
        val next = currentFlags and flag.inv()
        if (next != currentFlags) {
            currentFlags = next
            invalidate()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (!enabled) {
            currentFlags = StyleSheet.FLAG_DEFAULT
        }
        invalidate()
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
                if (isInside) {
                    performClick()
                }
                isPressed = false
                removeFlag(StyleSheet.FLAG_PRESSED)
                if (isInside && event.getToolType(0) != MotionEvent.TOOL_TYPE_FINGER) {
                    addFlag(StyleSheet.FLAG_HOVERED)
                } else {
                    removeFlag(StyleSheet.FLAG_HOVERED)
                }
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
        super.onDraw(canvas)
    }
}