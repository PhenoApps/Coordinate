package org.wheatgenetics.coordinate.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.interfaces.FieldsAdapterListener
import org.wheatgenetics.coordinate.optionalField.BaseOptionalField
import org.wheatgenetics.coordinate.optionalField.TimestampOptionalField

class FieldsAdapter(
    private val listener: FieldsAdapterListener,
    private val requiredName: String
) : ListAdapter<BaseOptionalField, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_field, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        getItem(position)?.let {

            with(holder as ViewHolder) {

                itemView.tag = holder
                

                bind(it.name, it.value, it.hint, object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(text: Editable?) {

                        it.value = text?.toString() ?: ""

                        if (it.name == requiredName) {

                            listener.onRequiredFieldCompleted()

                        }
                    }
                })
            }
        }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val valueEditText: EditText = view.findViewById(R.id.list_item_field_et)
        private val fieldTextView: TextView = view.findViewById(R.id.list_item_field_tv)
        private val scanButton: ImageButton = view.findViewById(R.id.list_item_scan_button)
        private var watcher: TextWatcher? = null
        fun bind(field: String, value: String, hint: String, watcher: TextWatcher) {

            with(view) {

                setOnClickListener {
                    valueEditText.requestFocus()
                }

                setupBarcodeScanner(getItem(bindingAdapterPosition).name)

                fieldTextView.text = field
                valueEditText.hint = hint
                valueEditText.setText(value)
                if (this@ViewHolder.watcher != null) {
                    valueEditText.removeTextChangedListener(this@ViewHolder.watcher)
                }
                this@ViewHolder.watcher = watcher
                valueEditText.addTextChangedListener(this@ViewHolder.watcher)

                // if the field is a TimestampOptionalField, disable the editText
                valueEditText.isEnabled = getItem(bindingAdapterPosition) !is TimestampOptionalField
            }
        }

        private fun setupBarcodeScanner(fieldName: String) {
            if (fieldName != requiredName) {
                scanButton.visibility = View.GONE
            } else {
                scanButton.visibility = View.VISIBLE
                scanButton.setOnClickListener {
                    listener.onBarcodeScanRequested()
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<BaseOptionalField>() {

        override fun areItemsTheSame(oldItem: BaseOptionalField, newItem: BaseOptionalField): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BaseOptionalField, newItem: BaseOptionalField): Boolean {
            return oldItem.name == newItem.name
        }
    }
}