package org.wheatgenetics.coordinate.fragments.app_intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.wheatgenetics.coordinate.R

class GallerySlideFragment : Fragment() {

    lateinit var images: IntArray

    private var slideTitle: String? = null
    private var slideSummary: String? = null
    private var slideBackgroundColor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        images = intArrayOf(
            R.drawable.ic_coordinate,
            R.drawable.ic_coordinate,
            R.drawable.ic_coordinate,
            R.drawable.ic_coordinate,
        )
        return inflater.inflate(R.layout.app_intro_gallery_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = view.findViewById<TextView>(R.id.title)
        val description = view.findViewById<TextView>(R.id.description)
        val gridLayout = view.findViewById<GridLayout>(R.id.grid_layout)

        title?.text = slideTitle
        description?.text = slideSummary

        slideBackgroundColor?.let { view.setBackgroundResource(it) }


        images.let { drawables ->
            // set # of rows and cols
            gridLayout.rowCount = 2
            gridLayout.columnCount = 2

            drawables.forEachIndexed { index, drawable ->
                val imageView = ImageView(context).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        // assign (row, col) position for an image
                        rowSpec = GridLayout.spec(index / 2, 1f)
                        columnSpec = GridLayout.spec(index % 2, 1f)
                        setMargins(16, 16, 16, 16)
                    }
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setImageDrawable(ContextCompat.getDrawable(requireContext(), drawable))
                }
                gridLayout.addView(imageView)
            }
        }
    }

    companion object {
        fun newInstance(
            slideTitle: String,
            slideSummary: String,
            slideBackgroundColor: Int
        ): GallerySlideFragment {
            val fragment = GallerySlideFragment()
            fragment.slideTitle = slideTitle
            fragment.slideSummary = slideSummary
            fragment.slideBackgroundColor = slideBackgroundColor
            return fragment
        }

    }
}