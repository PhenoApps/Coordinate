package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.utils.InsetHandler

class GridCreatorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_grid_creator)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val rootView = window.decorView.findViewById<android.view.View>(android.R.id.content)
        InsetHandler.setupStandardInsetsWithIme(rootView, toolbar)

        supportActionBar?.apply{
            title = getString(R.string.new_grid_title)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}