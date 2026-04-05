package dev1503.oreui.events

import android.view.MotionEvent
import android.view.View

fun interface OnHoverListener {
    fun onHover(view: View, event: MotionEvent)
}