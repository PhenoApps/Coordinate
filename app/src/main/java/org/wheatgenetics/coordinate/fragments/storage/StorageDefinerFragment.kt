package org.wheatgenetics.coordinate.fragments.storage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.phenoapps.fragments.storage.PhenoLibStorageDefinerFragment
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.DefineStorageActivity
import org.wheatgenetics.coordinate.preference.GeneralKeys
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil

@RequiresApi(Build.VERSION_CODES.KITKAT)
class StorageDefinerFragment: PhenoLibStorageDefinerFragment() {

    private val mDocumentTree = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->

        uri?.let { nonNulluri ->

            context?.let { ctx ->

                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

                with (ctx.contentResolver) {

//                    val lastPermitted = if (this?.persistedUriPermissions != null
//                        && this.persistedUriPermissions.isNotEmpty()) {
//                        this.persistedUriPermissions.first().uri
//                    } else null

                    //add new uri to persistable that the user just picked
                    this?.takePersistableUriPermission(nonNulluri, flags)

                    //release old storage directory from persistable if it exists
                    val oldPermitted = this?.persistedUriPermissions
                    if (oldPermitted != null && oldPermitted.isNotEmpty()) {
                        this?.persistedUriPermissions?.forEach {
                            if (it.uri != nonNulluri) {
                                releasePersistableUriPermission(it.uri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                            }
                        }
                    }

                    DocumentFile.fromTreeUri(ctx, nonNulluri)?.let { root ->

                        val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
                        if (prefs.getBoolean(GeneralKeys.MIGRATE_ASK_KEY, true)) {

                            //copy the HTPG.xml file to the newly defined folder
                            runBlocking(Dispatchers.IO) {

                                val exportDir = ctx.getString(R.string.FolderExport)
                                if (root.findFile(exportDir) == null) {
                                    DocumentTreeUtil.createDir(ctx, ctx.getString(R.string.FolderExport))
                                }

                                val templateDir = ctx.getString(R.string.FolderTemplate)
                                if (root.findFile(templateDir) == null) {
                                    DocumentTreeUtil.createDir(ctx, ctx.getString(R.string.FolderTemplate))
                                }

                                root.findFile(templateDir)?.let { templates ->

                                    if (templates.findFile("HTPG.xml") == null) {

                                        templates.createFile("*/*", "HTPG.xml")?.uri?.let { uri ->

                                            ctx.contentResolver.openOutputStream(uri)?.use { output ->

                                                ctx.resources.openRawResource(R.raw.htpg)
                                                    .copyTo(output)
                                            }
                                        }
                                    }
                                }
                            }

                            prefs.edit().putBoolean(GeneralKeys.MIGRATE_ASK_KEY, false).apply()
                        }
                        activity?.setResult(Activity.RESULT_OK)
                        activity?.finish()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val defineButton = view.findViewById<Button>(R.id.frag_storage_definer_choose_dir_btn)

        defineButton?.setOnClickListener { _ ->

            launchDefiner()

        }
    }

    override fun onTreeDefined(treeUri: Uri) {
        (activity as DefineStorageActivity).enableBackButton(false)
        super.onTreeDefined(treeUri)
        (activity as DefineStorageActivity).enableBackButton(true)
    }

        private fun launchDefiner() {
        context?.let {

            mDocumentTree.launch(null)

        }
    }
}