package org.wheatgenetics.coordinate.brapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.brapi.v2.model.geno.BrAPIPlate
import org.wheatgenetics.coordinate.R

class BrapiPlateAdapter(
    private val onSelectionChanged: (Int) -> Unit,
) : RecyclerView.Adapter<BrapiPlateAdapter.ViewHolder>() {

    private var allPlates: List<BrAPIPlate> = emptyList()
    private var displayList: List<BrAPIPlate> = emptyList()
    private val selected = mutableSetOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox: CheckBox = itemView.findViewById(R.id.plate_checkbox)
        val name: TextView = itemView.findViewById(R.id.plate_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brapi_plate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plate = displayList[position]
        val plateDbId = plate.plateDbId ?: ""

        holder.name.text = plate.plateName ?: plateDbId

        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = selected.contains(plateDbId)
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) selected.add(plateDbId) else selected.remove(plateDbId)
            onSelectionChanged(selected.size)
        }

        holder.itemView.setOnClickListener {
            holder.checkbox.isChecked = !holder.checkbox.isChecked
        }
    }

    override fun getItemCount(): Int = displayList.size

    fun setPlates(plates: List<BrAPIPlate>) {
        allPlates = plates
        displayList = plates
        selected.clear()
        onSelectionChanged(0)
        notifyDataSetChanged()
    }

    fun setDisplayList(plates: List<BrAPIPlate>) {
        displayList = plates
        notifyDataSetChanged()
    }

    fun getSelectedPlateDbIds(): List<String> = selected.toList()

    fun clearSelection() {
        selected.clear()
        onSelectionChanged(0)
        notifyDataSetChanged()
    }
}
