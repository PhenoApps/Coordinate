package org.wheatgenetics.coordinate.fragments.storage

import android.app.Activity
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import org.phenoapps.fragments.storage.PhenoLibStorageDefinerFragment
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.DefineStorageActivity
import javax.inject.Inject

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.KITKAT)
class StorageDefinerFragment: PhenoLibStorageDefinerFragment() {

    @Inject
    lateinit var prefs: SharedPreferences

    //default root folder name if user choose an incorrect root on older devices
    override val defaultAppName: String = "coordinate"

    //if this file exists the migrator will be skipped
    override val migrateChecker: String = ".coordinate"

    //define sample data and where to transfer
    override val samples = mapOf(
        AssetSample("resources", "HTGP.xml") to R.string.template_dir
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        //define directories that should be created in root storage
        context?.let { ctx ->
            val exports = ctx.getString(R.string.export_dir)
            val templates = ctx.getString(R.string.template_dir)
            directories = arrayOf(exports, templates)
        }
    }

    override fun onTreeDefined(treeUri: Uri) {
        (activity as DefineStorageActivity).enableBackButton(false)
        super.onTreeDefined(treeUri)
        (activity as DefineStorageActivity).enableBackButton(true)
    }

    override fun actionAfterDefine() {
        actionNoMigrate()
    }

    override fun actionNoMigrate() {
        activity?.setResult(Activity.RESULT_OK)
        activity?.finish()
    }
}