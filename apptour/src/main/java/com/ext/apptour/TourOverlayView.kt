package com.ext.apptour

import android.content.Context
import android.graphics.*
import android.view.View

class TourOverlayView(
    context: Context,
    private val targetView: View,
    private val overlayColor: Int,
    private val padding: Int,
    private val shape: HighlightShape
) : View(context) {

    private val backgroundPaint = Paint().apply {
        color = overlayColor
    }

    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        isAntiAlias = true
    }

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            backgroundPaint
        )

        val location = IntArray(2)
        targetView.getLocationOnScreen(location)

        val paddingF = padding.toFloat()

        val left = location[0].toFloat() - paddingF
        val top = location[1].toFloat() - paddingF
        val right = location[0].toFloat() + targetView.width + paddingF
        val bottom = location[1].toFloat() + targetView.height + paddingF

        when (shape) {

            HighlightShape.CIRCLE -> {

                val centerX = (left + right) / 2
                val centerY = (top + bottom) / 2

                val radius = maxOf(
                    targetView.width,
                    targetView.height
                ) / 2f + paddingF

                canvas.drawCircle(centerX, centerY, radius, clearPaint)
            }

            HighlightShape.RECTANGLE -> {

                canvas.drawRect(left, top, right, bottom, clearPaint)

            }

            HighlightShape.ROUNDED_RECTANGLE -> {

                canvas.drawRoundRect(
                    left,
                    top,
                    right,
                    bottom,
                    20f,
                    20f,
                    clearPaint
                )

            }
        }
    }
}