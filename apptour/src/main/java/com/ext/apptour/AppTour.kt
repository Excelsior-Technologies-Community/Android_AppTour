package com.ext.apptour

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class AppTour private constructor(
    private val activity: Activity
) {

    private val steps = mutableListOf<TourStep>()
    private var currentStepIndex = 0
    private var overlayColor: Int = Color.parseColor("#B3000000")
    private var tooltipBackground: Int = Color.WHITE
    private var titleTextColor: Int = Color.BLACK
    private var descriptionTextColor: Int = Color.DKGRAY
    private var highlightPadding = 20
    private var highlightShape = HighlightShape.CIRCLE

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

        currentStepIndex = 0
        showStep()
    }
    private fun showStep() {

        if (currentStepIndex >= steps.size) return

        val step = steps[currentStepIndex]

        val rootView = activity.window.decorView as ViewGroup

        val overlay = TourOverlayView(
            activity,
            step.view,
            overlayColor,
            highlightPadding,
            highlightShape
        )

        rootView.addView(
            overlay,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val tooltip = LayoutInflater.from(activity)
            .inflate(R.layout.tour_tooltip, rootView, false)

        val title = tooltip.findViewById<TextView>(R.id.tourTitle)
        val description = tooltip.findViewById<TextView>(R.id.tourDescription)
        val nextButton = tooltip.findViewById<Button>(R.id.nextButton)
        val skipButton = tooltip.findViewById<Button>(R.id.skipButton)

        title.text = step.title
        description.text = step.description

        rootView.addView(tooltip)

        val location = IntArray(2)
        step.view.getLocationOnScreen(location)

        tooltip.post {

            val viewLocation = IntArray(2)
            val rootLocation = IntArray(2)

            step.view.getLocationOnScreen(viewLocation)
            rootView.getLocationOnScreen(rootLocation)

            val relativeX = viewLocation[0] - rootLocation[0]
            val relativeY = viewLocation[1] - rootLocation[1]

            val viewBottom = relativeY + step.view.height

            tooltip.x = relativeX + (step.view.width / 2f) - (tooltip.width / 2f)

            tooltip.y = viewBottom + 30f
        }
        tooltip.setBackgroundColor(tooltipBackground)

        title.setTextColor(titleTextColor)

        description.setTextColor(descriptionTextColor)

        step.view.setOnClickListener {

            rootView.removeView(overlay)
            rootView.removeView(tooltip)

            currentStepIndex++

            showStep()
        }
        nextButton.setOnClickListener {

            rootView.removeView(overlay)
            rootView.removeView(tooltip)

            currentStepIndex++

            showStep()
        }
        skipButton.setOnClickListener {

            rootView.removeView(overlay)
            rootView.removeView(tooltip)

        }
    }
    fun setOverlayColor(color: Int): AppTour {
        overlayColor = color
        return this
    }

    fun setTooltipBackground(color: Int): AppTour {
        tooltipBackground = color
        return this
    }

    fun setTitleTextColor(color: Int): AppTour {
        titleTextColor = color
        return this
    }

    fun setDescriptionTextColor(color: Int): AppTour {
        descriptionTextColor = color
        return this
    }
    fun setHighlightPadding(padding: Int): AppTour {
        highlightPadding = padding
        return this
    }

    fun setHighlightShape(shape: HighlightShape): AppTour {
        highlightShape = shape
        return this
    }
}