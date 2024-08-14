package org.wheatgenetics.coordinate.utils

import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.view.View
import com.getkeepsafe.taptargetview.TapTarget
import org.wheatgenetics.coordinate.R

class TapTargetUtil {

    companion object {

        fun getTapTargetSettingsView(context: Context, view: View, title: String, desc: String, targetRadius: Int): TapTarget {
            val colorPrimaryDark = context.resources.getColor(R.color.colorPrimaryDark)
            return TapTarget.forView(view, title, desc)
                .outerCircleColorInt(colorPrimaryDark)
                .outerCircleAlpha(0.95f)
                .titleTextSize(30)
                .descriptionTextSize(20)
                .descriptionTypeface(Typeface.DEFAULT_BOLD)
                .drawShadow(true)
                .cancelable(false)
                .tintTarget(true)
                .transparentTarget(true)
                .targetRadius(targetRadius)
        }

        fun getTapTargetSettingsRect(context: Context, item: Rect, title: String, desc: String): TapTarget {
            val colorPrimaryDark = context.resources.getColor(R.color.colorPrimaryDark)
            return TapTarget.forBounds(item, title, desc) // All options below are optional
                .outerCircleColorInt(colorPrimaryDark) // Specify a color for the outer circle
                .outerCircleAlpha(0.95f) // Specify the alpha amount for the outer circle
                .titleTextSize(30) // Specify the size (in sp) of the title text
                .descriptionTextSize(20) // Specify the size (in sp) of the description text
                .descriptionTypeface(Typeface.DEFAULT_BOLD)
                .drawShadow(true) // Whether to draw a drop shadow or not
                .cancelable(false) // Whether tapping outside the outer circle dismisses the view
                .tintTarget(true) // Whether to tint the target view's color
                .transparentTarget(true) // Specify whether the target is transparent (displays the content underneath)
                .targetRadius(40)
        }
    }
}