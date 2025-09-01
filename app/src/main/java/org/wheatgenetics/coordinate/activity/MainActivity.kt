package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import org.wheatgenetics.coordinate.ui.CoordinateApp
import org.wheatgenetics.coordinate.ui.theme.CoordinateTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CoordinateTheme {
                CoordinateApp()
            }
        }
    }
}
