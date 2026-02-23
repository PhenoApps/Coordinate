package org.wheatgenetics.coordinate.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.interfaces.TitleSelectedListener

class TitleChoiceAdapter(
    private val listener: TitleSelectedListener,
    private val adapterType: AdapterType
) : ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback()) {

    private var mSelected: String? = null

    enum class AdapterType {
        PROJECT,
        TEMPLATE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_checked, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position == itemCount - 1) { // for the last position, bind the addNewItem tile (New Project/New Template)
            val newItemText = when (adapterType) {
                AdapterType.PROJECT -> holder.itemView.context.getString(R.string.frag_grid_creator_project_add_new_text)
                AdapterType.TEMPLATE -> holder.itemView.context.getString(R.string.frag_grid_creator_new_template_title)
            }
            (holder as ViewHolder).bind(newItemText, true) {
                listener.onAddNewItemClicked()
            }
        } else { // for all other positions
            getItem(position)?.let { title ->
                with(holder as ViewHolder) {
                    itemView.tag = holder
                    bind(title, false) {
                        if (adapterType == AdapterType.PROJECT) {
                            mSelected = title
                            this@TitleChoiceAdapter.notifyItemRangeChanged(0, itemCount)
                        }
                        listener.checked(title)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1 // +1 for the addNewItemTile at the end
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(value: String, isAddNewItemTile: Boolean, onClick: View.OnClickListener) {
            with(view as CheckedTextView) {
                setOnClickListener(onClick)
                text = value
                if (isAddNewItemTile || adapterType == AdapterType.TEMPLATE) {
                    // hide the check mark for the add item tile and for template items
                    // (template selection navigates immediately, no checkbox needed)
                    checkMarkDrawable = null
                } else {
                    // PROJECT type: restore checkmark drawable in case this ViewHolder was
                    // previously used for the add-new-item tile (checkMarkDrawable = null)
                    if (checkMarkDrawable == null) {
                        val a = context.obtainStyledAttributes(
                            intArrayOf(android.R.attr.listChoiceIndicatorMultiple)
                        )
                        checkMarkDrawable = a.getDrawable(0)
                        a.recycle()
                    }
                    isChecked = text.toString() == mSelected

                    // Set or reset the tint based on selected state
                    if (isChecked) {
                        val color = ContextCompat.getColor(context, R.color.colorAccent)
                        checkMarkTintList = ColorStateList.valueOf(color)
                    } else {
                        checkMarkTintList = null
                    }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}