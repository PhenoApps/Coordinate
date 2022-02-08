package org.wheatgenetics.coordinate.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.preference.PreferenceManager
import org.wheatgenetics.coordinate.activity.DefineStorageActivity

@RequiresApi(Build.VERSION_CODES.KITKAT)
class DocumentTreeUtil {

    /**
     * Static functions to be used to handle exports.
     * Typical structure is Coordinate/Exports or Coordinate/Templates.
     * These functions will attempt to create these directories if they do not exist.
     */
    companion object {

        private const val STORAGE_ASK_KEY = "org.wheatgenetics.coordinate.preferences.first_ask_document_tree_set"

        /**
         * Checks whether the user has been asked to set a root directory for file storage.
         * This is only asked once, but the user can always access this by going to the settings.
         */
        fun checkDocumentTreeSet(ctx: Context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
                if (prefs.getBoolean(STORAGE_ASK_KEY, true)) {

                    ctx.startActivity(Intent(ctx, DefineStorageActivity::class.java))

                    prefs.edit().putBoolean(STORAGE_ASK_KEY, false).apply()
                }
            }
        }

        /**
         * Checks whether a persisted uri has been saved; therefore, defined by the user.
         */
        fun isEnabled(ctx: Context): Boolean {
            val persists = ctx.contentResolver.persistedUriPermissions
            return persists.isNotEmpty()
        }

        fun createFile(ctx: Context, parent: String, name: String): DocumentFile? {

            val dir = createDir(ctx, parent)
            return dir?.createFile("*/*", name)

        }

        fun createDir(ctx: Context, parent: String, child: String): DocumentFile? {

            val file = createDir(ctx, parent)
            return if (file?.findFile(child)?.isDirectory == true) {
                file.findFile(child)
            } else file?.createDirectory(child)
        }

        /**
         * Finds the persisted uri and creates the basic coordinate file structure if it doesn't exist.
         */
        private fun createDir(ctx: Context, parent: String): DocumentFile? {
            val persists = ctx.contentResolver.persistedUriPermissions
            if (persists.isNotEmpty()) {
                val uri = persists.first().uri
                DocumentFile.fromTreeUri(ctx, uri)?.let { tree ->
                    var file = tree.findFile("Coordinate")
                    if (file == null) {
                        file = tree.createDirectory("Coordinate")
                    }
                    var exportDir = file?.findFile(parent)
                    if (exportDir == null) {
                        exportDir = file?.createDirectory(parent)
                    }

                    return exportDir
                }
            }

            return null
        }
    }
}