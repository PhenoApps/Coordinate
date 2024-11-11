package org.wheatgenetics.coordinate.pc

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.zxing.integration.android.IntentIntegrator
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.utils.SoftKeyboardUtil

class CreateProjectDialogFragment : DialogFragment() {

    private var handler: Handler? = null
    private var projectTitleEditText: EditText? = null
    private var createProjectAlertDialog: AlertDialog? = null

    private val barcodeScannerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result -> parseActivityResult(result.resultCode, result.data) }

    companion object {
        fun newInstance(handler: Handler): CreateProjectDialogFragment {
            return CreateProjectDialogFragment().apply {
                this.handler = handler
            }
        }
    }

    private fun parseActivityResult(resultCode: Int, data: Intent?): Boolean {
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result == null) {
            return false
        } else {
            val barcodeScannerResult = result.contents
            if (barcodeScannerResult != null && barcodeScannerResult.isNotEmpty()) {
                setProjectTitle(barcodeScannerResult)
            }
            return true
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = layoutInflater.inflate(R.layout.create_project, null)
        projectTitleEditText = view.findViewById(R.id.projectTitleEditText)
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.CreateProjectAlertDialogTitle)
            .setView(view)
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                projectTitleEditText?.let { SoftKeyboardUtil.closeKeyboard(requireActivity(), it) }
                dialog.cancel()
            }
            .setNeutralButton(R.string.tutorial_collector_barcode_title, null)

        createProjectAlertDialog = builder.create()

        createProjectAlertDialog?.setOnShowListener { dialog ->
            val neutralButton = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_NEUTRAL)
            val positiveButton = (dialog).getButton(AlertDialog.BUTTON_POSITIVE)

            // don't dismiss dialog when scanning
            neutralButton.setOnClickListener {
                scanBarcode()
            }

            positiveButton.setOnClickListener {
                createProject()
            }
        }

        return createProjectAlertDialog as AlertDialog
    }

    private fun createProject() {
        val projectTitle = projectTitleEditText?.text.toString()
        if (projectTitle.isEmpty()) {
            Toast.makeText(context, R.string.CreateProjectAlertDialogEmptyToast, Toast.LENGTH_SHORT).show()
        } else if (handler?.handleCreateProjectDone(projectTitle) == true) {
            projectTitleEditText?.let { SoftKeyboardUtil.closeKeyboard(requireActivity(), it) }
            dismiss()
        } else {
            Toast.makeText(context, R.string.CreateProjectAlertDialogInUseToast, Toast.LENGTH_SHORT).show()
        }
    }

    private fun scanBarcode() {
        IntentIntegrator(requireActivity()).apply {
            setOrientationLocked(false)
            setPrompt("Scan a barcode")
            setBeepEnabled(true)
        }.createScanIntent().also {
            barcodeScannerLauncher.launch(it)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun setProjectTitle(title: String) {
        projectTitleEditText?.setText(title)
    }

    interface Handler {
        fun handleCreateProjectDone(projectTitle: String): Boolean
    }
}