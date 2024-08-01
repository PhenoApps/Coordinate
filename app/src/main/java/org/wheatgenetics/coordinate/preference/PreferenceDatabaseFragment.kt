package org.wheatgenetics.coordinate.preference

import android.app.AlertDialog
import android.content.*
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.Preference
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.database.SampleData
import org.wheatgenetics.coordinate.deleter.GridDeleter
import org.wheatgenetics.coordinate.deleter.ProjectDeleter
import org.wheatgenetics.coordinate.deleter.TemplateDeleter
import org.wheatgenetics.coordinate.utils.DateUtil
import org.wheatgenetics.coordinate.utils.ZipUtil
import org.wheatgenetics.coordinate.utils.DocumentTreeUtil
import org.wheatgenetics.coordinate.utils.Utils as AppUtils

import java.io.*
import java.lang.Exception
import android.provider.OpenableColumns
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

import java.io.*


class PreferenceDatabaseFragment : BasePreferenceFragment(), OnSharedPreferenceChangeListener {
    // region Fields

    companion object {
        const val TAG = "PreferenceDatabase"
    }
    private var sharedPreferences: SharedPreferences? = null

    private val importChooser = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        it?.let { uri ->
            importDatabase(uri)
        }
    }

    private val exportChooser = registerForActivityResult(ActivityResultContracts.CreateDocument()) {
        it?.let { uri ->
            exportDatabase(uri)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.database_preferences)
        setupDatabaseResetPreference()
        setupExportDatabasePreference()
        setupImportDatabasePreference()
        setupReloadDatabasePreference()

        super.setToolbar(getString(R.string.pref_database_title))

        super.setupBottomNavigationBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                context?.let { ctx ->
                    val intent = PreferenceActivity.intent(ctx)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupExportDatabasePreference() {
        if (isAdded) {
            context?.let { ctx ->
                val databaseExportKey = GeneralKeys.EXPORT_DATABASE
                val preference = findPreference<Preference>(databaseExportKey)
                preference?.setOnPreferenceClickListener {
                    AlertDialog.Builder(ctx)
                        .setTitle(R.string.dialog_database_export_title)
                        .setPositiveButton(android.R.string.ok) { dialog: DialogInterface?, _: Int ->
                            val fileName = "Coordinate_Output_${DateUtil().getTime().replace(":", "_")}.zip"
                            if (DocumentTreeUtil.isEnabled(ctx)) {
                                exportDatabase(fileName)
                            } else {
                                exportChooser.launch(fileName)
                            }
                            dialog?.dismiss()
                        }
                        .setNegativeButton(android.R.string.cancel) { dialog: DialogInterface?, _: Int ->
                            dialog?.dismiss()
                        }
                        .show()
                    true
                }
            }
        }
    }

    private fun setupImportDatabasePreference() {
        if (isAdded) {
            val databaseImportKey = GeneralKeys.IMPORT_DATABASE
            val preference = findPreference<Preference>(databaseImportKey)
            preference?.setOnPreferenceClickListener {
                AlertDialog.Builder(activity)
                    .setTitle(R.string.dialog_database_import_title)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        importChooser.launch(arrayOf("*/*"))
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .create()
                    .show()
                true
            }
        }
    }

    private fun setupReloadDatabasePreference() {
        if (isAdded){
            val databaseReloadKey = getString(R.string.key_pref_database_reload)
            val preference = findPreference<Preference>(databaseReloadKey)
            preference?.setOnPreferenceClickListener {
                showDatabaseReloadDialog()
                true
            }
        }
    }

    // First confirmation
    private fun showDatabaseReloadDialog() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.dialog_warning))
        builder.setMessage(getString(R.string.database_reload_warning))

        builder.setPositiveButton(getString(R.string.dialog_yes)) { dialog, _ ->
            if (context != null){
                resetDatabase(context)
                SampleData(context).insertSampleData()
            }

            dialog.dismiss()
            AppUtils.makeToast(context, getString(R.string.database_reload_message))
        }

        builder.setNegativeButton(getString(R.string.dialog_no)) { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }


    /**
     * Creates and shows a dialog to the user to confirm db deletion.
     * Clears all rows in grid, project and entries tables.
     * Only the user templates are deleted.
     */
    private fun setupDatabaseResetPreference() {
        if (isAdded) {
            val databaseResetKey = getString(R.string.pref_database_reset_key)
            val databaseResetPreference = findPreference<Preference>(databaseResetKey)
            databaseResetPreference?.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    showDatabaseResetDialog1()
                    true
                }
        }
    }

    // First confirmation
    private fun showDatabaseResetDialog1() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.dialog_warning))
        builder.setMessage(getString(R.string.database_reset_warning1))

        builder.setPositiveButton(getString(R.string.dialog_yes)) { dialog, _ ->
            dialog.dismiss()
            showDatabaseResetDialog2()
        }

        builder.setNegativeButton(getString(R.string.dialog_no)) { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    // Second confirmation
    private fun showDatabaseResetDialog2() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.dialog_warning))
        builder.setMessage(getString(R.string.database_reset_warning2))

        builder.setPositiveButton(getString(R.string.dialog_yes)) { dialog, _ ->
            if (context != null) {
                resetDatabase(context)

                dialog.dismiss()
                AppUtils.makeToast(context, getString(R.string.database_reset_message))
            }

            try {
                activity?.finishAffinity()
            } catch (e: Exception) {
                Log.e("Coordinate", e.message ?: "Error")
            }
        }

        builder.setNegativeButton(getString(R.string.dialog_no)) { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun resetDatabase(ctx: Context?) {
        if (ctx != null) {
            val gd = GridDeleter(ctx) {}
            val pd = ProjectDeleter(ctx) { _: Long -> }
            val td = TemplateDeleter(ctx, TemplateDeleter.GridHandler {})

            //grid deleter deletes all grids and entries to the grids
            gd.deleteAll()

            //template deleter will delete all user-defined templates
            td.deleteAllUserTemplates()

            //project delete deletes all projects
            pd.deleteAll()
        }
    }

    private fun importDatabase(uri: Uri) {

        try {

            context?.let { ctx ->

                getFileName(uri)?.let { name ->

                    //first check if the file to import is just a .db file
                    when {

                        name.endsWith(".db") -> { //if it is import it old-style

                            val dbPath = ctx.getDatabasePath("seedtray1.db")

                            ctx.contentResolver.openInputStream(uri).use { input ->
                                ctx.contentResolver.openOutputStream(dbPath.toUri()).use { output ->
                                    output?.let { out ->
                                        input?.copyTo(out)
                                    }
                                }
                            }
                        }
                        else -> { //otherwise unzip and import prefs as well

                            val internalDbPath: String = ctx.getDatabasePath("seedtray1.db").path

                            ZipUtil.unzip(ctx,
                                ctx.contentResolver.openInputStream(uri),
                                FileOutputStream(internalDbPath)
                            )
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFileName(uri: Uri): String? {

        var result: String? = null

        if (uri.scheme == "content") {
            context?.contentResolver?.query(uri, null, null, null, null)?.let { cursor ->
                cursor.use { c ->
                    if (c.moveToFirst()) {
                        val index = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (index > 0) {
                            result = c.getString(index)
                        }
                    }
                }
            }
        }

        if (result == null) {
            uri.path?.let { path ->
                val cut = path.lastIndexOf('/')
                if (cut != -1) {
                    result = path.substring(cut + 1)
                }
            }
        }

        return result
    }

    private fun exportDatabase(fileName: String) {

        context?.let { ctx ->

            try {
                val databaseDir = DocumentTreeUtil.createDir(ctx, "Database")
                if (databaseDir != null && databaseDir.exists()) {
                    val zipFile = databaseDir.createFile("*/*", fileName)
                    if (zipFile != null && zipFile.exists()) {
                        exportDatabase(zipFile.uri)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun exportDatabase(uri: Uri) {

        context?.let { ctx ->

            runBlocking(Dispatchers.IO) {

                //get database path and shared preferences path
                val dbPath: String = context?.getDatabasePath("seedtray1.db")?.path ?: ""

                //zip files into stream
                try {
                    val tempOutput = File("${context?.externalCacheDir?.path}/temp")
                    val fileStream = FileOutputStream(tempOutput)
                    val objectStream = ObjectOutputStream(fileStream)
                    val zipOutput = context?.contentResolver?.openOutputStream(uri)
                    val prefs = Utils.getDefaultSharedPreferences(ctx)
                    objectStream.writeObject(prefs?.all)
                    ZipUtil.zip(arrayOf(dbPath, tempOutput.path), zipOutput)
                    objectStream.close()
                    fileStream.close()
                    if (!tempOutput.delete()) {
                        throw IOException()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            Toast.makeText(ctx, R.string.database_export_success, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {}
}