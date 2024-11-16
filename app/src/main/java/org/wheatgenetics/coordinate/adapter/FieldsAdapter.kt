package org.wheatgenetics.coordinate.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.interfaces.RequiredFieldsCompleteListener
import org.wheatgenetics.coordinate.optionalField.BaseOptionalField
import org.wheatgenetics.coordinate.optionalField.NonNullOptionalFields
import org.wheatgenetics.coordinate.optionalField.TimestampOptionalField

class FieldsAdapter(private val listener: RequiredFieldsCompleteListener, private val requiredName: String, private val fields: NonNullOptionalFields) :
    ListAdapter<BaseOptionalField, RecyclerView.ViewHolder>(DiffCallback()) {

    // linkedMapOf to preserve the order when doing 'get'
    private val values = linkedMapOf<String, String>()

    init {
        fields.forEach { 
            values[it.name] = it.value
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_field, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        getItem(position)?.let {

            with(holder as ViewHolder) {

                itemView.tag = holder
                
                bind(it.name, this@FieldsAdapter.values[it.name] ?: it.value, it.hint, object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(text: Editable?) {

                        if (it.name == requiredName) {

                            listener.completed()

                        }

                        this@FieldsAdapter.values[it.name] = text?.toString() ?: ""
                    }
                })
            }
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val valueEditText: EditText = view.findViewById(R.id.list_item_field_et)
        private val fieldTextView: TextView = view.findViewById(R.id.list_item_field_tv)
        private var watcher: TextWatcher? = null
        fun bind(field: String, value: String, hint: String, watcher: TextWatcher) {

            with(view) {
                
                setOnClickListener { 
                    valueEditText.requestFocus()
                }
                
                fieldTextView.text = field
                valueEditText.hint = hint
                valueEditText.setText(this@FieldsAdapter.values[field])
                if (this@ViewHolder.watcher != null) {
                    valueEditText.removeTextChangedListener(this@ViewHolder.watcher)
                }
                this@ViewHolder.watcher = watcher
                valueEditText.addTextChangedListener(this@ViewHolder.watcher)

                // if the field is a TimestampOptionalField, disable the editText
                valueEditText.isEnabled = getItem(bindingAdapterPosition) !is TimestampOptionalField
            }
        }
    }

    fun getAllFieldValues(): Map<String, String> = values.toMap()

    class DiffCallback : DiffUtil.ItemCallback<BaseOptionalField>() {

        override fun areItemsTheSame(oldItem: BaseOptionalField, newItem: BaseOptionalField): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BaseOptionalField, newItem: BaseOptionalField): Boolean {
            return oldItem.name == newItem.name
        }
    }
}