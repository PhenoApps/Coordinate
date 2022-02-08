package org.wheatgenetics.coordinate.fragments.template_creator

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.database.TemplatesTable

/***
 * Two spinners that change the row/column enumeration formatting.
 */
class TemplateCreatorNaming : Fragment(R.layout.fragment_template_creator_naming) {

    private val args: TemplateCreatorNamingArgs by navArgs()

    private var mTemplateTable: TemplatesTable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mTemplateTable = TemplatesTable(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
    }

    /**
     * Listens for changes in the spinners and updates the cnumb/rnumb database columns
     * when the ok button is pressed.
     * *numb == 0 means the enumeration will be alphabetic 0 corresponds to the position of the spinner
     * array which is "1,2,3" or "A,B,C"
     */
    private fun setupSpinners() {

        val rowsSpinner = view?.findViewById<Spinner>(R.id.frag_template_creator_naming_rows_spn)
        val colsSpinner = view?.findViewById<Spinner>(R.id.frag_template_creator_naming_columns_spn)
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->

            rowsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                    template.rowNumbering = position != 0
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            colsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                    template.colNumbering = position != 0
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            okButton?.setOnClickListener {
                mTemplateTable?.update(template)
                Toast.makeText(activity,
                    getString(R.string.TemplateCreatedToast, args.title), Toast.LENGTH_SHORT).show()
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }

            backButton?.setOnClickListener {
                findNavController().navigate(TemplateCreatorNamingDirections
                    .actionTemplateNamingPop())
            }
        }
    }
}