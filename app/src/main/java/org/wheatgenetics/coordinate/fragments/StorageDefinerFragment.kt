package org.wheatgenetics.coordinate.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import org.wheatgenetics.coordinate.R
import java.io.File
import java.io.FileInputStream

@RequiresApi(Build.VERSION_CODES.KITKAT)
class StorageDefinerFragment: Fragment(R.layout.fragment_storage_definer) {

    private val mPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        result?.let { permissions ->
            if (!permissions.containsValue(false)) {
                //input is an optional uri that would define the folder to start from
                mDocumentTree.launch(null)
            } else {
                activity?.setResult(Activity.RESULT_CANCELED)
                activity?.finish()
            }
        }
    }

    private val mDocumentTree = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->

        uri?.let { nonNulluri ->

            context?.let { ctx ->

                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION

                with (context?.contentResolver) {

                    val lastPermitted = if (this?.persistedUriPermissions != null
                        && this.persistedUriPermissions.isNotEmpty()) {
                        this.persistedUriPermissions.first().uri
                    } else null

                    //add new uri to persistable that the user just picked
                    this?.takePersistableUriPermission(nonNulluri, flags)

                    DocumentFile.fromTreeUri(ctx, uri)?.let { root ->

                        migrateStorage(lastPermitted, root)

                    }

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
                }
            }
        }

        //finish activity
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }

    /**
     * Copy all folders from old uri to new uri.
     * If from uri is null, check the default <29 SDK root Coordinate folder.
     */
    private fun migrateStorage(from: Uri?, to: DocumentFile) {

        if (from == null) {

            migrateOld(to)

        } else {

            context?.let { ctx ->

                DocumentFile.fromTreeUri(ctx, from)?.let { tree ->

                    tree.findFile("Coordinate")?.let { root ->
                        copy(to.uri, to, root)
                    }
                }
            }
        }
    }

    private fun copy(root: Uri, output: DocumentFile, file: DocumentFile) {

        if (root != file.uri) { //do not permit infinite recursion

            file.name?.let { name ->

                if (!file.isDirectory) {

                    output.createFile("*/*", name)?.let { docFile ->

                        context?.contentResolver?.openInputStream(file.uri)?.use { input ->

                            context?.contentResolver?.openOutputStream(docFile.uri)?.use { output ->

                                input.copyTo(output)

                            }
                        }
                    }

                } else {

                    val dir = output.findFile(name)

                    if (dir == null || !dir.isDirectory) {

                        output.createDirectory(name)?.breadthCopy(root, file)

                    } else dir.breadthCopy(root, file)
                }
            }
        }
    }

    private fun copy(output: DocumentFile, file: File) {

        if (!file.isDirectory) {

            output.createFile("*/*", file.name)?.let { docFile ->

                FileInputStream(file).use { input ->

                    context?.contentResolver?.openOutputStream(docFile.uri)?.use { output ->

                        input.copyTo(output)

                    }
                }
            }

        } else {

            val dir = output.findFile(file.name)

            if (dir == null || !dir.isDirectory) {

                output.createDirectory(file.name)?.breadthCopy(file)

            } else dir.breadthCopy(file)

        }
    }

    private fun DocumentFile.breadthCopy(f: File) {
        f.listFiles()?.forEach { f ->
            copy(this, f)
        }
    }

    private fun DocumentFile.breadthCopy(root: Uri, file: DocumentFile) {
        file.listFiles().forEach { f ->
            copy(root, this, f)
        }
    }

    private fun migrateOld(to: DocumentFile) {

        context?.let {

            val oldCoordinate = File("${Environment.getExternalStorageDirectory()}/Coordinate")

            copy(to, oldCoordinate)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val defineButton = view.findViewById<Button>(R.id.frag_storage_definer_choose_dir_btn)
        val skipButton = view.findViewById<Button>(R.id.frag_storage_definer_skip_btn)

        defineButton?.setOnClickListener { _ ->

            context?.let { ctx ->

                activity?.let { act ->

                    launchDefiner()

                }
            }
        }

        skipButton?.setOnClickListener {
            activity?.setResult(Activity.RESULT_CANCELED)
            activity?.finish()
        }
    }

    private fun launchDefiner() {
        context?.let { ctx ->

            //request runtime permissions for storage
            if (ActivityCompat.checkSelfPermission(ctx,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ctx,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                //input is an optional uri that would define the folder to start from
                mDocumentTree.launch(null)

            } else {

                mPermissions.launch(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE))

            }
        }
    }
}