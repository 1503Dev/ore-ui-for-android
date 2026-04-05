package dev1503.oreui.events

import android.view.MotionEvent
import android.view.View

interface OnUnhoverListener {
    fun onUnhover(view: View, event: MotionEvent)
}