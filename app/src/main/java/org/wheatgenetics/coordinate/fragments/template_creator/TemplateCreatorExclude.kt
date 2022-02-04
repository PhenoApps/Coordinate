package org.wheatgenetics.coordinate.fragments.template_creator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.handler.SelectionHandler
import com.evrencoskun.tableview.listener.ITableViewListener
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.StringGetter
import org.wheatgenetics.coordinate.adapter.GridExcludeAdapter
import org.wheatgenetics.coordinate.collector.Collector
import org.wheatgenetics.coordinate.database.TemplatesTable
import org.wheatgenetics.coordinate.model.*
import java.util.ArrayList
import kotlin.random.Random

typealias GridCell = org.wheatgenetics.coordinate.model.Cell
class TemplateCreatorExclude : Fragment(R.layout.fragment_template_exclude),
    ITableViewListener,
    StringGetter {

    data class Cell(val text: String)

    private val args: TemplateCreatorExcludeArgs by navArgs()

    private var mTemplateTable: TemplatesTable? = null
    private var mRandomSelections: ArrayList<Pair<Int, Int>> = ArrayList()
    private var mRows = 0
    private var mCols = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mTemplateTable = TemplatesTable(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadGridData()
        setupButtons()
    }

    private fun setupButtons() {

        val resetButton = view?.findViewById<Button>(R.id.frag_template_exclude_reset_btn)
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)
        val randomEditText = view?.findViewById<EditText>(R.id.frag_template_creator_exclude_random_et)

        resetButton?.setOnClickListener {
            val table = view?.findViewById<TableView>(R.id.frag_template_exclude_table)
            (table?.adapter as? GridExcludeAdapter)?.clearSelection()
            table?.adapter?.notifyDataSetChanged()
        }

        //going back we must clear the excluded selection (if user changes dimensions of grid an error will occur)
        backButton?.setOnClickListener {
            writeToDatabase()
            mRandomSelections.clear()
            randomEditText?.setText(String())
            findNavController().navigate(TemplateCreatorExcludeDirections
                .actionTemplateExcludePop())
        }

        okButton?.setOnClickListener {
            writeToDatabase()
            mRandomSelections.clear()
            randomEditText?.setText(String())
            findNavController().navigate(TemplateCreatorExcludeDirections
                .actionTemplateExcludeToTemplateNaming(args.title))
        }

        randomEditText?.addTextChangedListener { it?.toString()?.let { text ->

            addRandomExclusions(text.toIntOrNull() ?: 0)

        } }
    }

    private fun clearDatabaseExclusions(temp: TemplateModel) {

        for (i in 0 until mRows) {
            for (j in 0 until mCols) {
                val cell = GridCell(i + 1, j + 1, this)
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
                    val cell = GridCell(pair.first+1, pair.second+1, this)
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
                val cells = arrayListOf<List<Cell>>()

                //load items into adapter
                rows.forEachIndexed { _, m ->
                    val row = arrayListOf<Cell>()
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
                        if (template.isExcludedCell(GridCell(i+1, j+1, this))) {
                            (table.adapter as GridExcludeAdapter).setSelected(i, j)
                        }
                    }
                }
            }
        }
    }

    override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
        view?.findViewById<TableView>(R.id.frag_template_exclude_table)?.let { table ->
            (table.adapter as GridExcludeAdapter).setSelected(row, column)
            table.adapter?.notifyDataSetChanged()
        }
    }

    //region unimplemented click events
    override fun onCellDoubleClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {}
    override fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {}
    override fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        view?.findViewById<TableView>(R.id.frag_template_exclude_table)?.let { table ->
            for (i in 0 until mRows) {
                (table.adapter as GridExcludeAdapter).setSelected(i, column)
            }
            table.adapter?.notifyDataSetChanged()
        }
    }
    override fun onColumnHeaderDoubleClicked(
        columnHeaderView: RecyclerView.ViewHolder,
        column: Int
    ) {}
    override fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int) {}
    override fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        view?.findViewById<TableView>(R.id.frag_template_exclude_table)?.let { table ->
            for (i in 0 until mCols) {
                (table.adapter as GridExcludeAdapter).setSelected(row, i)
            }
            table.adapter?.notifyDataSetChanged()
        }
    }
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