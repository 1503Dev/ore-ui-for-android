package dev1503.oreui.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import dev1503.oreui.StyleSheet

class OreTabs @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    interface OnTabChangeListener {
        fun onTabChanged(index: Int, button: OreButton)
    }

    private val onTabChangeListeners = mutableListOf<OnTabChangeListener>()
    private var _activeIndex: Int = -1

    var activeIndex: Int
        get() = _activeIndex
        set(value) {
            if (value in 0 until childCount && value != _activeIndex) {
                _activeIndex = value
                updateButtonsState()
                val button = getChildAt(value) as? OreButton
                if (button != null) {
                    onTabChangeListeners.forEach { it.onTabChanged(value, button) }
                }
            } else if (value == -1) {
                _activeIndex = -1
                updateButtonsState()
            }
        }

    init {
        orientation = HORIZONTAL
        clipChildren = false
        clipToPadding = false
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun addOnTabChangeListener(listener: OnTabChangeListener) {
        onTabChangeListeners.add(listener)
    }

    fun removeOnTabChangeListener(listener: OnTabChangeListener) {
        onTabChangeListeners.remove(listener)
    }

    fun addButton(button: OreButton) {
        val params = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        button.layoutParams = params

        button.setOnClickListener {
            if (!isEnabled) return@setOnClickListener
            val index = indexOfChild(button)
            if (index != -1) {
                activeIndex = index
            }
        }

        addView(button)

        if (childCount == 1 && _activeIndex == -1) {
            activeIndex = 0
        } else {
            updateButtonsState()
        }
    }

    fun removeButton(button: OreButton) {
        val index = indexOfChild(button)
        if (index != -1) {
            removeView(button)
            if (_activeIndex == index) {
                _activeIndex = -1
                if (childCount > 0) {
                    activeIndex = 0
                }
            } else if (_activeIndex > index) {
                _activeIndex--
            }
            updateButtonsState()
        }
    }

    fun clearButtons() {
        removeAllViews()
        _activeIndex = -1
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateButtonsState()
    }

    private fun updateButtonsState() {
        val count = childCount
        if (count == 0) return

        val parentEnabled = isEnabled
        val p = (getChildAt(0) as? OreButton)?.styleSheet?.pixelSize ?: 5f

        for (i in 0 until count) {
            val child = getChildAt(i) as? OreButton ?: continue

            child.isEnabled = parentEnabled

            if (i == _activeIndex) {
                child.addFlag(StyleSheet.FLAG_ACTIVE)
                child.z = 1f
            } else {
                child.removeFlag(StyleSheet.FLAG_ACTIVE)
                child.z = 0f
            }

            val params = child.layoutParams as LayoutParams
            if (i > 0) {
                params.leftMargin = (-p).toInt()
            } else {
                params.leftMargin = 0
            }
            child.layoutParams = params
        }
        invalidate()
    }
}