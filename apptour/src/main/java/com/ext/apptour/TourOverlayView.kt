package com.ext.apptour

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

class TourOverlayView(context: Context) : View(context) {

    private val paint = Paint().apply {
        color = Color.parseColor("#B3000000")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            paint
        )
    }

    // allow touches to pass through
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }
}