package org.wheatgenetics.coordinate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.interfaces.TitleSelectedListener

class TitleChoiceAdapter(private val listener: TitleSelectedListener) :
    ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback()) {

    private var mSelected: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_checked, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        getItem(position)?.let { title ->

            with(holder as ViewHolder) {

                itemView.tag = holder

                bind(title) {

                    mSelected = title

                    this@TitleChoiceAdapter.notifyItemRangeChanged(0, itemCount)

                    listener.checked(title)
                }
            }
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(value: String, onClick: View.OnClickListener) {

            with(view as CheckedTextView) {

                setOnClickListener(onClick)

                text = value

                isChecked = text.toString() == mSelected

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