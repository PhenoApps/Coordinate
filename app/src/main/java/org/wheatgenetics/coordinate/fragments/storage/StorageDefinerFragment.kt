package org.wheatgenetics.coordinate.fragments.storage

import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import org.phenoapps.fragments.storage.PhenoLibStorageDefinerFragment
import org.phenoapps.security.Security
import org.phenoapps.utils.BaseDocumentTreeUtil
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.preference.GeneralKeys
import javax.inject.Inject

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.KITKAT)
class StorageDefinerFragment: PhenoLibStorageDefinerFragment() {

    @Inject
    lateinit var prefs: SharedPreferences

    private val advisor by Security().secureDocumentTree()

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

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        prefs.edit().putBoolean(GeneralKeys.FROM_INTRO_AUTOMATIC, true).apply()

        advisor.defineDocumentTree({ treeUri ->

            runBlocking {

                directories?.let { dirs ->

                    BaseDocumentTreeUtil.defineRootStructure(context, treeUri, dirs)?.let { root ->

                        samples.entries.forEach { entry ->

                            val sampleAsset = entry.key
                            val dir = entry.value

                            BaseDocumentTreeUtil.copyAsset(context, sampleAsset.name, sampleAsset.dir, dir)
                        }

                        activity?.setResult(Activity.RESULT_OK)
                        activity?.finish()
                    }
                }
            } },
            {
                activity?.finish()
            }
        )
    }

    override fun onResume() {
        super.onResume()
        if (prefs.getBoolean(GeneralKeys.FROM_INTRO_AUTOMATIC, false)) {
            prefs.edit().putBoolean(GeneralKeys.FROM_INTRO_AUTOMATIC, false).apply()
        } else activity?.finish()
    }
}