package org.wheatgenetics.coordinate.preference

import android.app.AlertDialog
import android.content.*
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.deleter.GridDeleter
import org.wheatgenetics.coordinate.deleter.ProjectDeleter
import org.wheatgenetics.coordinate.deleter.TemplateDeleter
import org.wheatgenetics.coordinate.utils.DateUtil
import org.wheatgenetics.coordinate.utils.ZipUtil
import java.io.*
import java.lang.Exception
import java.util.*
import android.provider.OpenableColumns
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController


class PreferenceDatabaseFragment : PreferenceFragmentCompat(), OnSharedPreferenceChangeListener {
    // region Fields

    companion object {
        const val TAG = "PreferenceDatabase"
    }
    private var databaseResetPreference: Preference? = null
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
        val databaseResetKey = getString(R.string.pref_database_reset_key)
        this.databaseResetPreference = findPreference(databaseResetKey)
        setupDatabaseResetPreference()
        setupExportDatabasePreference()
        setupImportDatabasePreference()
        setupBackButton()
    }

    private fun setupBackButton() {

        setHasOptionsMenu(true)
        activity?.let { act ->
            val bar = (act as AppCompatActivity).supportActionBar
            bar?.setHomeButtonEnabled(true)
            bar?.setDisplayHomeAsUpEnabled(true)
        }
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
            val databaseExportKey = getString(R.string.key_pref_database_export)
            val preference = findPreference<Preference>(databaseExportKey)
            preference?.setOnPreferenceClickListener {
                AlertDialog.Builder(activity)
                    .setTitle(R.string.dialog_database_export_title)
                    .setPositiveButton(android.R.string.ok) { dialog: DialogInterface?, which: Int ->
                        val fileName = "Coordinate_Output_${DateUtil().getTime()}.zip"
                        exportChooser.launch(fileName)
                    }
                    .setNegativeButton(android.R.string.cancel) { dialog: DialogInterface?, which: Int -> }
                    .create()
                    .show()
                true
            }
        }
    }

    private fun setupImportDatabasePreference() {
        if (isAdded) {
            val databaseImportKey = getString(R.string.key_pref_database_import)
            val preference = findPreference<Preference>(databaseImportKey)
            preference?.setOnPreferenceClickListener {
                AlertDialog.Builder(activity)
                    .setTitle(R.string.dialog_database_import_title)
                    .setPositiveButton(android.R.string.ok) { dialog: DialogInterface?, which: Int ->
                        importChooser.launch(arrayOf("*/*"))
                    }
                    .setNegativeButton(android.R.string.cancel) { dialog: DialogInterface?, which: Int -> }
                    .create()
                    .show()
                true
            }
        }
    }

    /**
     * Creates and shows a dialog to the user to confirm db deletion.
     * Clears all rows in grid, project and entries tables.
     * Only the user templates are deleted.
     */
    private fun setupDatabaseResetPreference() {
        if (isAdded) {
            databaseResetPreference?.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    AlertDialog.Builder(activity)
                        .setTitle(R.string.dialog_database_reset_title)
                        .setPositiveButton(android.R.string.ok) { dialog: DialogInterface?, which: Int ->
                            val ctx = context
                            if (ctx != null) {
                                val gd = GridDeleter(ctx) {}
                                val pd = ProjectDeleter(ctx) { a: Long -> }
                                val td = TemplateDeleter(ctx, TemplateDeleter.GridHandler {})

                                //grid deleter deletes all grids and entries to the grids
                                gd.deleteAll()

                                //template deleter will delete all user-defined templates
                                td.deleteAllUserTemplates()

                                //project delete deletes all projects
                                pd.deleteAll()
                            }
                        }
                        .setNegativeButton(android.R.string.cancel) { dialog: DialogInterface?, which: Int -> }
                        .create()
                        .show()
                    true
                }
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

    private fun exportDatabase(uri: Uri) {

        context?.let { ctx ->

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

            //use media scanner on the output
            //Utils.scanFile(context, zipFile)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {}
}