package org.wheatgenetics.coordinate.utils

import android.content.Context
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Plays a sound from the raw resources folder.
 */
class SoundHelperImpl(private val context: Context) {

    companion object {
        const val PLONK = "plonk"
        const val ROW_OR_COLUMN_END = "row_or_column_end"
        const val DUPLICATE = "unsure"
    }

    fun playPlonk() {
        playSound(PLONK)
    }

    fun playRowOrColumnEnd() {
        playSound(ROW_OR_COLUMN_END)
    }

    fun playDuplicate() {
        playSound(DUPLICATE)
    }

    private fun playSound(sound: String?) {
        try {
            val resID: Int = context.resources.getIdentifier(sound, "raw", context.packageName)
            val chimePlayer = MediaPlayer.create(context, resID)
            chimePlayer.start()
            chimePlayer.setOnCompletionListener { mp -> mp.release() }
        } catch (ignore: Exception) { }
    }
}