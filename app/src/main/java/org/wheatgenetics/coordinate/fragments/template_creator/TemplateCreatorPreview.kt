package org.wheatgenetics.coordinate.fragments.template_creator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.listener.ITableViewListener
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.adapter.GridPreviewAdapter
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.model.*

class TemplateCreatorPreview : Fragment(R.layout.fragment_template_preview),
    ITableViewListener,
    StringGetter {

    private val args: TemplateCreatorExcludeRandomArgs by navArgs()

    private var mTemplateTable: TemplatesTable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mTemplateTable = TemplatesTable(context)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigateBack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set toolbar back button
        setHasOptionsMenu(true)

        loadGridData()
        setupButtons()
    }

    private fun setupButtons() {

        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        backButton?.setOnClickListener {
            navigateBack()
        }

        okButton?.setOnClickListener {
            Toast.makeText(context, getString(R.string.TemplateCreatedToast, args.title),
                Toast.LENGTH_SHORT).show()
            activity?.setResult(Activity.RESULT_OK)
            activity?.finish()
        }
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun loadGridData() {

        view?.findViewById<TableView>(R.id.frag_template_preview_table)?.let { table ->

            mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->

                table.setHasFixedWidth(true)

                table.tableViewListener = this

                table.isShowHorizontalSeparators = false

                table.isShowVerticalSeparators = false

                table.selectionHandler.isShadowEnabled = false

                table.isIgnoreSelectionColors = true

                val rows = (1..template.rows).map { Cell(it.toString()) }
                val cols = (1..template.cols).map { Cell(it.toString()) }
                val cells = arrayListOf<List<Cell>>()

                //load items into adapter
                rows.forEachIndexed { _, m ->
                    val row = arrayListOf<Cell>()
                    cols.forEachIndexed { _, n ->
                        row.add(Cell("${m.text}-${n.text}"))
                    }
                    cells.add(row)
                }

                val adapter = GridPreviewAdapter()
                table.setAdapter(adapter)

                adapter.setAllItems(cols, rows, cells)

                var numExcluded = 0
                //update ui selection based on previous exclusion
                rows.forEachIndexed { i, _ ->
                    cols.forEachIndexed { j, _ ->
                        if (template.isExcludedCell(Cell(i+1, j+1, this))) {
                            numExcluded++
                            (table.adapter as GridPreviewAdapter).setSelected(i, j)
                        }
                    }
                }

                val additionalTextView = view?.findViewById<TextView>(R.id.frag_template_creator_preview_additional_tv)

                val colLabels = if (template.colNumbering) getString(R.string.numeric) else getString(R.string.alphabetic)
                val rowLabels = if (template.rowNumbering) getString(R.string.numeric) else getString(R.string.alphabetic)

                additionalTextView?.text = getString(R.string.frag_template_creator_preview_additional_info, colLabels, rowLabels, numExcluded.toString())
            }
        }
    }

    override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {}
    //region unimplemented click events
    override fun onCellDoubleClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {}
    override fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {}
    override fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {}
    override fun onColumnHeaderDoubleClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {}
    override fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int) {}
    override fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {}
    override fun onRowHeaderDoubleClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {}
    override fun onRowHeaderLongPressed(rowHeaderView: RecyclerView.ViewHolder, row: Int) {}

    override fun get(resId: Int): String? {
        return context?.getString(resId)
    }

    @SuppressLint("ResourceType")
    override fun getQuantity(resId: Int, quantity: Int, vararg formatArgs: Any?): String {
        return context?.getString(resId, formatArgs) ?: ""
    }
    //endregion
}