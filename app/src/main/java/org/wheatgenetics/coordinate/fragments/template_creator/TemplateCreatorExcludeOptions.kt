package org.wheatgenetics.coordinate.fragments.template_creator

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wheatgenetics.coordinate.R

class TemplateCreatorExcludeOptions : Fragment(R.layout.fragment_template_exclude_options) {

    private val args: TemplateCreatorExcludeRandomArgs by navArgs()

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

        setupButtons()
    }

    private fun setupButtons() {

        val randomButton = view?.findViewById<Button>(R.id.frag_template_exclude_random_btn)
        val selectionButton = view?.findViewById<Button>(R.id.frag_template_exclude_selection_btn)
        val okButton = view?.findViewById<Button>(R.id.frag_next_btn)
        val backButton = view?.findViewById<Button>(R.id.frag_back_btn)

        backButton?.setOnClickListener {
            navigateBack()
        }

        okButton?.setOnClickListener {
            findNavController().navigate(TemplateCreatorExcludeOptionsDirections
                .actionTemplateExcludeOptionsToTemplatePreview(args.title))
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

    private fun navigateBack() {
        //going back we must clear the excluded selection (if user changes dimensions of grid an error will occur)
        findNavController().navigate(TemplateCreatorExcludeOptionsDirections
            .actionTemplateExcludePop())
    }
}