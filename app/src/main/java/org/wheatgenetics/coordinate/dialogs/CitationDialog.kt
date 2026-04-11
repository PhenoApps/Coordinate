package org.wheatgenetics.coordinate.dialogs

import android.content.Context
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import org.wheatgenetics.coordinate.R

class CitationDialog(private val context: Context) {

    fun show() {
        val citationUrl = context.getString(R.string.citation_url)
        val citationMessage = context.getString(R.string.citation_message)
        val citationText = context.getString(R.string.citation_text)

        val htmlMessage = "$citationMessage<br/><br/><a href=\"$citationUrl\"><tt>$citationText</tt></a>"

        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.citation_title)
            .setMessage(HtmlCompat.fromHtml(htmlMessage, HtmlCompat.FROM_HTML_MODE_LEGACY))
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { d, _ ->
                d.dismiss()
            }
            .create()

        dialog.show()
        dialog.findViewById<TextView>(android.R.id.message)?.movementMethod =
            LinkMovementMethod.getInstance()
    }
}
