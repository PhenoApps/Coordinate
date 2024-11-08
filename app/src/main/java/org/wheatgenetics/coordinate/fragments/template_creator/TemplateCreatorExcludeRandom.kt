package org.wheatgenetics.coordinate.fragments.template_creator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.listener.ITableViewListener
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.adapter.GridExcludeAdapter
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.model.Cell
import org.wheatgenetics.coordinate.model.TemplateModel
import kotlin.random.Random

class TemplateCreatorExcludeRandom : Fragment(R.layout.fragment_template_exclude_random),
    ITableViewListener,
    StringGetter {

    private val args: TemplateCreatorExcludeRandomArgs by navArgs()

    private var mTemplateTable: TemplatesTable? = null
    private var mRandomSelections: ArrayList<Pair<Int, Int>> = ArrayList()
    private var mRows = 0
    private var mCols = 0

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
        val randomEditText = view?.findViewById<EditText>(R.id.frag_template_creator_exclude_random_et)

        backButton?.setOnClickListener {
            navigateBack()
        }

        okButton?.setOnClickListener {
            writeToDatabase()
            mRandomSelections.clear()
            randomEditText?.setText(String())
            findNavController().navigate(TemplateCreatorExcludeRandomDirections
                .actionTemplateExcludeToTemplatePreview(args.title))
        }

        randomEditText?.addTextChangedListener { it?.toString()?.let { text ->

            addRandomExclusions(text.toIntOrNull() ?: 0)

        } }
    }

    private fun navigateBack() {
        val randomEditText = view?.findViewById<EditText>(R.id.frag_template_creator_exclude_random_et)

        //going back we must clear the excluded selection (if user changes dimensions of grid an error will occur)
        val table = view?.findViewById<TableView>(R.id.frag_template_exclude_table)
        (table?.adapter as? GridExcludeAdapter)?.clearSelection()
        table?.adapter?.notifyDataSetChanged()
        writeToDatabase()
        mRandomSelections.clear()
        randomEditText?.setText(String())
        findNavController().navigate(TemplateCreatorExcludeRandomDirections
            .actionTemplateExcludePop())
    }

    private fun clearDatabaseExclusions(temp: TemplateModel) {

        for (i in 0 until mRows) {
            for (j in 0 until mCols) {
                val cell = Cell(i + 1, j + 1, this)
                if (temp.isExcludedCell(cell)) {
                    temp.remove(cell)
                }
            }
        }
    }

    private fun addRandomExclusions(n: Int) {

        view?.findViewById<TableView>(R.id.frag_template_exclude_table)?.let { table ->

            val adapter = (table.adapter as GridExcludeAdapter)

            //reset old random selections
            mRandomSelections.forEach {
                adapter.setSelected(it.first, it.second)
            }

            mRandomSelections.clear()

            // clear the old selection from the grid
            // if this is not done, the new random excluded
            // cells will appear in addition to the previously excluded
            adapter.clearSelection()

            //get all cells that aren't selected already
            val nonExcluded = arrayListOf<Pair<Int, Int>>()
            for (i in 0 until mRows) {
                for (j in 0 until mCols) {
                    val cell = Pair(i, j)
                    if (!adapter.isSelected(i, j)) {
                        nonExcluded.add(cell)
                    }
                }
            }

            //iterate n (random) num times and choose one non excluded cell to exclude
            for (i in 0 until n) {
                if (nonExcluded.size == 0) break
                val cell = nonExcluded[Random.nextInt(0, nonExcluded.size)]
                nonExcluded.remove(cell)
                mRandomSelections.add(cell)
                adapter.setSelected(cell.first, cell.second)
            }
        }
    }

    private fun writeToDatabase() {

        view?.findViewById<TableView>(R.id.frag_template_exclude_table)?.let { table ->

            mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->

                //clear old exclusions
                clearDatabaseExclusions(template)

                //update exclusions based on UI
                val selections = (table.adapter as GridExcludeAdapter).selection
                for (pair in selections) {
                    val cell = Cell(pair.first+1, pair.second+1, this)
                    if (!template.isExcludedCell(cell)) template.add(cell)
                }

                template.generatedExcludedCellsAmount = 0

                mTemplateTable?.update(template)
            }
        }
    }

    /**
     * Uses the convertDatabaseToTable query to create a spreadsheet of values.
     * Columns returned are plot_id followed by all traits.
     */
    private fun loadGridData() {

        view?.findViewById<TableView>(R.id.frag_template_exclude_table)?.let { table ->

            mTemplateTable?.load()?.find { it.title == args.title }?.let { template ->

                table.setHasFixedWidth(true)

                table.tableViewListener = this

                table.isShowHorizontalSeparators = false

                table.isShowVerticalSeparators = false

                table.selectionHandler.isShadowEnabled = false

                table.isIgnoreSelectionColors = true

                mRows = template.rows
                mCols = template.cols

                val rows = (1..template.rows).map { Cell(it.toString()) }
                val cols = (1..template.cols).map { Cell(it.toString()) }
                val cells = arrayListOf<List<org.wheatgenetics.coordinate.fragments.template_creator.Cell>>()

                //load items into adapter
                rows.forEachIndexed { _, m ->
                    val row = arrayListOf<org.wheatgenetics.coordinate.fragments.template_creator.Cell>()
                    cols.forEachIndexed { _, n ->
                        row.add(Cell("${m.text}-${n.text}"))
                    }
                    cells.add(row)
                }

                val adapter = GridExcludeAdapter()
                table.setAdapter(adapter)

                adapter.setAllItems(cols, rows, cells)

                //update ui selection based on previous exclusion
                rows.forEachIndexed { i, _ ->
                    cols.forEachIndexed { j, _ ->
                        if (template.isExcludedCell(Cell(i+1, j+1, this))) {
                            (table.adapter as GridExcludeAdapter).setSelected(i, j)
                        }
                    }
                }
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