package org.wheatgenetics.coordinate.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment.Companion.createInstance
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.fragments.app_intro.GallerySlideFragment
import org.wheatgenetics.coordinate.fragments.app_intro.OptionalSetupFragment
import org.wheatgenetics.coordinate.fragments.app_intro.RequiredSetupPolicyFragment

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
                R.drawable.ic_coordinate,
                R.color.colorPrimaryDark
            )
        )

        // gallery slide
//        addSlide(
//            GallerySlideFragment.newInstance(
//                context.getString(R.string.app_intro_intro_title_slide2),
//                context.getString(R.string.app_intro_intro_summary_slide2),
//                R.color.colorPrimaryLight
//            )
//        )

        // required setup
        addSlide(
            RequiredSetupPolicyFragment.newInstance(
                context.getString(R.string.app_intro_required_setup_title),
                context.getString(R.string.app_intro_required_setup_summary),
                R.color.colorPrimaryDark
            )
        )

        // optional setup
        addSlide(
            OptionalSetupFragment.newInstance(
                context.getString(R.string.app_intro_optional_setup_title),
                context.getString(R.string.app_intro_optional_setup_summary),
                R.color.colorPrimaryDark
            )
        )
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        setResult(RESULT_OK)
        finish()
    }
}