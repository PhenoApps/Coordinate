package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import org.wheatgenetics.coordinate.R

class GridCreatorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_grid_creator)

        supportActionBar?.apply{
            title = getString(R.string.new_grid_title)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}