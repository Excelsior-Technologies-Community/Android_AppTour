package com.ext.apptour

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class AppTour private constructor(
    private val activity: Activity
) {

    private val steps = mutableListOf<TourStep>()

    companion object {

        fun with(activity: Activity): AppTour {
            return AppTour(activity)
        }
    }

    fun addStep(step: TourStep): AppTour {
        steps.add(step)
        return this
    }

    fun start() {

        if (steps.isEmpty()) return

        val step = steps[0]

        val rootView = activity.window.decorView as ViewGroup

        val overlay = TourOverlayView(activity)

        rootView.addView(
            overlay,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val tooltip = LayoutInflater.from(activity)
            .inflate(R.layout.tour_tooltip, rootView, false)

        val title = tooltip.findViewById<TextView>(R.id.tourTitle)
        val description = tooltip.findViewById<TextView>(R.id.tourDescription)

        title.text = step.title
        description.text = step.description

        rootView.addView(tooltip)

        val location = IntArray(2)
        step.view.getLocationOnScreen(location)

        tooltip.post {
            tooltip.x = location[0].toFloat()
            tooltip.y = (location[1] + step.view.height + 20).toFloat()
        }

        // when user clicks the target view
        step.view.setOnClickListener {

            rootView.removeView(overlay)
            rootView.removeView(tooltip)

        }
    }
}