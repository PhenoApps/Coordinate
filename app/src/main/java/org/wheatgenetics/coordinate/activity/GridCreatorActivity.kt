package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

import org.wheatgenetics.coordinate.R

class GridCreatorActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_grid_creator)
        
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        supportActionBar?.apply{
            title = getString(R.string.new_grid_title)
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}