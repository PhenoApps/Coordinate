package org.wheatgenetics.coordinate.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.preference.PreferenceManager
import org.phenoapps.utils.BaseDocumentTreeUtil.Companion.getRoot
import org.phenoapps.utils.BaseDocumentTreeUtil.Companion.getStem
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.DefineStorageActivity
import org.wheatgenetics.coordinate.preference.GeneralKeys

@RequiresApi(Build.VERSION_CODES.KITKAT)
class DocumentTreeUtil {

    /**
     * Static functions to be used to handle exports.
     * Typical structure is Coordinate/Exports or Coordinate/Templates.
     * These functions will attempt to create these directories if they do not exist.
     */
    companion object {

        enum class CheckDocumentResult(val value: Int) {
            EXISTS(1), DEFINE(2), DISMISS(3)
        }

        /**
         * Checks whether the user has been asked to set a root directory for file storage.
         * This is only asked once, but the user can always access this by going to the settings.
         */
        fun checkDocumentTreeSet(ctx: Context) {

            val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
            if (prefs.getBoolean(GeneralKeys.STORAGE_ASK_KEY, true)) {

                ctx.startActivity(Intent(ctx, DefineStorageActivity::class.java))

                prefs.edit().putBoolean(GeneralKeys.STORAGE_ASK_KEY, false).apply()
            }
        }


        fun getPath(ctx: Context): String? {
            //get directory name
            val root = getRoot(ctx)
            if (root != null && root.exists())
            {
                var path = root.uri.lastPathSegment
                if (path == null) {
                    // default to directory name if path is null
                    path = root.uri.getStem(ctx)
                }
                return path
            }
            return null
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
         * Function that checks if the persisted folder exists.
         * If it does not exist, show a dialog asking the user to define it.
         * @param ctx the calling context
         * @param function the callback, true if the user selects to define a storage
         */
        fun checkDir(ctx: Context?, function: (CheckDocumentResult) -> Unit) {

            var persisted = false

            ctx?.contentResolver?.persistedUriPermissions?.let { perms ->

                if (perms.isNotEmpty()) {

                    perms.first()?.uri?.let { uri ->

                        persisted = DocumentFile.fromTreeUri(ctx, uri)?.exists() ?: false

                    }
                }
            }

            if (!persisted) {

                AlertDialog.Builder(ctx)
                    .setNegativeButton(android.R.string.no) { dialog, which ->

                        dialog.dismiss()

                        function(CheckDocumentResult.DISMISS)

                    }
                    .setPositiveButton(android.R.string.yes) { dialog, which ->

                        dialog.dismiss()

                        function(CheckDocumentResult.DEFINE)
                    }
                    .setTitle(R.string.document_tree_undefined)
                    .create()
                    .show()

            } else {

                function(CheckDocumentResult.EXISTS)
            }
        }

        /**
         * Finds the persisted uri and creates the basic coordinate file structure if it doesn't exist.
         */
        fun createDir(ctx: Context, parent: String): DocumentFile? {
            val persists = ctx.contentResolver.persistedUriPermissions
            if (persists.isNotEmpty()) {
                val uri = persists.first().uri
                DocumentFile.fromTreeUri(ctx, uri)?.let { tree ->
                    var exportDir = tree.findFile(parent)
                    if (exportDir == null) {
                        exportDir = tree.createDirectory(parent)
                    }

                    return exportDir
                }
            }

            return null
        }
    }
}