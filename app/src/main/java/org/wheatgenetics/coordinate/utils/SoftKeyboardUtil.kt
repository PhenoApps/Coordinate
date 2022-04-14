package org.wheatgenetics.coordinate.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Utility file for opening / closing the soft keyboard.
 * usage java: SoftKeyboardUtil.Companion.closeKeyboard(getActivity(), editText, null)
 *             -- pass in your context, view, null for default delay or specify your own
 * kotlin: SoftKeyboardUtil.closeKeyboard(context, view) <-- no need to pass delay if using default
 */
class SoftKeyboardUtil {
    companion object {

        //by default the delay to open is 1/4 a second
        private const val DEFAULT_DELAY = 250L

        //extension method to get Input Method Manager from the given context
        private fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        fun closeKeyboard(context: Context?, view: View, delay: Long? = DEFAULT_DELAY) {
            context?.let { ctx ->
                with(ctx.getInputMethodManager()) {
                    view.postDelayed({
                        hideSoftInputFromWindow(view.windowToken, 0)
                    }, delay ?: DEFAULT_DELAY)
                }
            }
        }

        fun showKeyboard(context: Context?, view: View, delay: Long? = DEFAULT_DELAY) {
            context?.let { ctx ->
                with(ctx.getInputMethodManager()) {
                    view.postDelayed({
                        showSoftInput(view, 0)
                    }, delay ?: DEFAULT_DELAY)
                }
            }
        }
    }
}