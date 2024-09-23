package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment.Companion.createInstance
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.fragments.app_intro.SetupPolicyFragment

class AppIntroActivity : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext

        isSkipButtonEnabled = false

        // intro slide
        addSlide(
            createInstance(
                context.getString(R.string.app_intro_intro_title_slide1),
                context.getString(R.string.app_intro_intro_summary_slide1),
                R.drawable.other_ic_coordinate,
                R.color.colorPrimaryLight
            )
        )

        addSlide(
            SetupPolicyFragment.newInstance(
                context.getString(R.string.app_intro_setup_title),
                context.getString(R.string.app_intro_setup_summary),
                R.color.colorPrimaryLight
            )
        )
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        setResult(RESULT_OK)
        finish()
    }
}