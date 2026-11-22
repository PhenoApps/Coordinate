package org.wheatgenetics.coordinate.utils

import android.content.res.Configuration
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

object InsetHandler {

    private val systemBarsAndCutout =
        WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()

    @JvmStatic
    fun enableEdgeToEdge(activity: ComponentActivity) {
        activity.enableEdgeToEdge()
        val decor = activity.window.decorView as ViewGroup
        fun addScrim(gravity: Int): View {
            val view = View(activity).apply {
                setBackgroundColor(Color.BLACK)
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
            }
            decor.addView(view, FrameLayout.LayoutParams(0, 0, gravity))
            return view
        }
        fun resizeScrim(view: View, width: Int, height: Int) {
            if (view.layoutParams.width != width || view.layoutParams.height != height) {
                view.updateLayoutParams<FrameLayout.LayoutParams> {
                    this.width = width
                    this.height = height
                }
            }
        }
        // edge-to-edge keeps system bars transparent, so protection belongs in app content
        val leftScrim = addScrim(Gravity.LEFT)
        val rightScrim = addScrim(Gravity.RIGHT)
        ViewCompat.setOnApplyWindowInsetsListener(leftScrim) { _, insets ->
            val landscape = activity.resources.configuration.orientation ==
                Configuration.ORIENTATION_LANDSCAPE
            val cutout = insets.getInsets(WindowInsetsCompat.Type.displayCutout())
            resizeScrim(leftScrim, if (landscape) cutout.left else 0,
                ViewGroup.LayoutParams.MATCH_PARENT)
            resizeScrim(rightScrim, if (landscape) cutout.right else 0,
                ViewGroup.LayoutParams.MATCH_PARENT)
            insets
        }
        ViewCompat.requestApplyInsets(leftScrim)
    }

    /**
     * Applies top system bar inset as padding to a toolbar view, pushing its content below the
     * status bar. Use this for activities that also have a BottomNavigationView — call
     * applyBottomNavInsets() separately for the BNV.
     * Callable from Java via InsetHandler.applyToolbarInsets(view).
     */
    @JvmStatic
    fun applyToolbarInsets(toolbar: View) {
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            v.updatePadding(top = bars.top)
            insets
        }
        ViewCompat.requestApplyInsets(toolbar)
    }

    /**
     * Applies bottom system bar insets as padding to a view (typically a BottomNavigationView).
     * Prevents content from being hidden behind the gesture navigation bar on Android 15+.
     * Callable from Java via InsetHandler.applyBottomNavInsets(view).
     */
    @JvmStatic
    fun applyBottomNavInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            v.updatePadding(bottom = bars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(view)
    }

    /**
     * Applies left and right system bar/cutout insets as padding to a root view. Use this in BNV
     * activities to keep all content bounded between the camera cutout and the navigation bar in
     * landscape mode. Callable from Java via InsetHandler.applyRootInsets(view).
     */
    @JvmStatic
    fun applyRootInsets(rootView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            v.updatePadding(left = bars.left, right = bars.right)
            insets
        }
        ViewCompat.requestApplyInsets(rootView)
    }

    @JvmStatic
    fun applyRootInsetsWithIme(rootView: View, bottomNav: View) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            val imeBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            bottomNav.visibility = if (imeBottom > 0) View.GONE else View.VISIBLE
            view.updatePadding(left = bars.left, right = bars.right, bottom = imeBottom)
            insets
        }
        ViewCompat.requestApplyInsets(rootView)
    }

    /**
     * Applies system bar insets to both a toolbar (top) and a root view (bottom + left + right),
     * and also handles IME (keyboard) insets on the root view so that bottom-anchored buttons are
     * pushed above the soft keyboard when it appears.
     * Use this for activities that contain text input fields (e.g. grid/template creator flows).
     * Callable from Java via InsetHandler.setupStandardInsetsWithIme(rootView, toolbar).
     */
    @JvmStatic
    fun setupStandardInsetsWithIme(rootView: View, toolbar: View) {
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            v.updatePadding(top = bars.top)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val bars = insets.getInsets(
                systemBarsAndCutout or WindowInsetsCompat.Type.ime()
            )
            v.updatePadding(bottom = bars.bottom, left = bars.left, right = bars.right)
            insets
        }
        ViewCompat.requestApplyInsets(rootView)
    }

    /**
     * Applies system bar insets to both a toolbar (top) and a root view (bottom + left + right).
     * Use this for activities that do NOT have a BottomNavigationView.
     * The toolbar receives paddingTop = status bar height (content appears below the status bar).
     * The rootView receives bottom/left/right padding to keep content away from nav bar and camera
     * cutout in all orientations.
     * Callable from Java via InsetHandler.setupStandardInsets(rootView, toolbar).
     */
    @JvmStatic
    fun setupStandardInsets(rootView: View, toolbar: View) {
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            v.updatePadding(top = bars.top)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            v.updatePadding(bottom = bars.bottom, left = bars.left, right = bars.right)
            insets
        }
        ViewCompat.requestApplyInsets(rootView)
    }

    /**
     * Sizes a dedicated "scrim" view to exactly the status bar height, giving it a persistent
     * colorPrimary background behind the transparent status bar. Because the AppCompat action mode
     * bar is positioned below the status bar area (it starts at y=statusBarHeight), it does not
     * cover this region, so this scrim keeps the status bar colored during action mode.
     * Add a View with id status_bar_scrim at the top of the layout (height="0dp") and call this
     * in onCreate after setContentView.
     * Callable from Java via InsetHandler.applyStatusBarScrim(view).
     */
    @JvmStatic
    fun applyStatusBarScrim(scrimView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(scrimView) { v, insets ->
            val bars = insets.getInsets(systemBarsAndCutout)
            val lp = v.layoutParams
            lp.height = bars.top
            v.layoutParams = lp
            insets
        }
        ViewCompat.requestApplyInsets(scrimView)
    }
}
