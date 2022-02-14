package org.wheatgenetics.coordinate.fragments.template_creator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wheatgenetics.coordinate.R

class TemplateCreatorExcludeOptions : Fragment(R.layout.fragment_template_exclude_options) {

    private val args: TemplateCreatorExcludeRandomArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {

        val randomButton = view?.findViewById<Button>(R.id.frag_template_exclude_random_btn)
        val selectionButton = view?.findViewById<Button>(R.id.frag_template_exclude_selection_btn)
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        //going back we must clear the excluded selection (if user changes dimensions of grid an error will occur)
        backButton?.setOnClickListener {
            findNavController().navigate(TemplateCreatorExcludeOptionsDirections
                .actionTemplateExcludePop())
        }

        okButton?.setOnClickListener {
            findNavController().navigate(TemplateCreatorExcludeOptionsDirections
                .actionTemplateExcludeOptionsToTemplateNaming(args.title))
        }

        randomButton?.setOnClickListener {
            findNavController().navigate(TemplateCreatorExcludeOptionsDirections
                .actionTemplateExcludeOptionsToTemplateExcludeRandom(args.title))
        }

        selectionButton?.setOnClickListener {
            findNavController().navigate(TemplateCreatorExcludeOptionsDirections
                .actionTemplateExcludeOptionsToTemplateExcludeSelection(args.title))
        }
    }
}