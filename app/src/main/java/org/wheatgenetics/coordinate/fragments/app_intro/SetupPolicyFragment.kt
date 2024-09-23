package org.wheatgenetics.coordinate.fragments.app_intro

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.github.appintro.SlidePolicy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.phenoapps.androidlibrary.Utils
import org.phenoapps.utils.BaseDocumentTreeUtil
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.activity.AppIntroActivity
import org.wheatgenetics.coordinate.activity.DefineStorageActivity
import org.wheatgenetics.coordinate.preference.GeneralKeys
import org.wheatgenetics.coordinate.views.SetupItemView
import pub.devrel.easypermissions.EasyPermissions

class SetupPolicyFragment : Fragment(), SlidePolicy {
    private var slideTitle: String? = null
    private var slideSummary: String? = null
    private var slideBackgroundColor: Int? = null

//    private var permissionsSetupItem: SetupItemView? = null
    private var storageDefinerSetupItem: SetupItemView? = null
    private var loadSampleDataSetupItem: SetupItemView? = null

    private var prefs: SharedPreferences? = null

    private val scope by lazy { CoroutineScope(Dispatchers.Main) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.app_intro_required_setup_slide, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }

        val slideTitle = view.findViewById<TextView>(R.id.slide_title)
        val slideSummary = view.findViewById<TextView>(R.id.slide_summary)

        slideTitle?.text = this.slideTitle
        slideSummary?.text = this.slideSummary

        slideBackgroundColor?.let { view.setBackgroundResource(it) }


//        permissionsSetupItem = view.findViewById(R.id.permissions_setup_item)
        storageDefinerSetupItem = view.findViewById(R.id.storage_definer_setup_item)
        loadSampleDataSetupItem = view.findViewById(R.id.load_sample_data_setup_item)

        initSetupItems()
    }

    private fun initSetupItems() {
//        permissionsSetupItem?.apply {
//            setIcon(R.drawable.ic_pref_profile_person)
//            hideCheckbox()
//        }

        storageDefinerSetupItem?.apply {
            setIcon(R.drawable.ic_storage_white)
            setTitle(getString(R.string.app_intro_storage_title))
            setSummary(getString(R.string.app_intro_storage_summary))
            hideCheckbox()
        }

        loadSampleDataSetupItem?.apply {
            setIcon(R.drawable.ic_collect_data_black)
            setTitle(getString(R.string.app_intro_load_sample_data_title))
            setSummary(getString(R.string.app_intro_load_sample_data_summary))
            hideStatus()
            hideIcon()
        }

        storageDefinerSetupItem?.setOnClickListener {
            scope.launch {

                requestStorageDefiner()

                withContext(Dispatchers.Default) {
                    while (!validateStorage()) {
                        // check every 100ms if the item is set up i.e. call back is completed
                        delay(100)
                    }
                }

                checkSetupStatus(storageDefinerSetupItem, validateStorage())

            }
        }

        loadSampleDataSetupItem?.setOnClickListener {
            loadSampleToggle()
        }

        // request permissions initially without needing to tap
        // requestPermissions()

        checkSetupStatus(storageDefinerSetupItem, validateStorage())
    }

    private fun checkSetupStatus(setupItemView: SetupItemView?, isSet: Boolean) {
        if (isSet) {
            setupItemView?.setStatus(R.drawable.ic_check_white)
        }
    }

    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(activity as AppIntroActivity, Constants.permissions, 12)
    }

    private fun requestStorageDefiner() {
        startActivity(Intent(activity as AppIntroActivity, DefineStorageActivity::class.java))
    }

    private fun validatePermissions(): Boolean {
        var permissionsGranted = false

        var perms = arrayOf<String?>(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        context?.let {
            permissionsGranted = EasyPermissions.hasPermissions(it, *perms)
        }

        return permissionsGranted
    }

    private fun validateStorage(): Boolean {
        val root = BaseDocumentTreeUtil.getRoot(context)
        return (root != null && root.exists())
    }

    private fun loadSampleToggle() {
        loadSampleDataSetupItem?.let {
            it.setCheckbox(!it.isChecked())

            prefs?.edit()?.putBoolean(GeneralKeys.LOAD_SAMPLE_DATA, it.isChecked())?.apply()
        }
    }

    override val isPolicyRespected: Boolean
        get() = validateItems()

    override fun onUserIllegallyRequestedNextPage() {
//        if (!validatePermissions()) {
//            Utils.showShortToast(context, "Permissions not granted")
//        } else
        if (!validateStorage()) {
            Utils.showShortToast(context, getString(R.string.app_intro_storage_warning))
        }
    }

    private fun validateItems(): Boolean {
        return validateStorage()
    }

    companion object {
        fun newInstance(
            slideTitle: String,
            slideSummary: String,
            slideBackgroundColor: Int
        ): SetupPolicyFragment {
            val fragment = SetupPolicyFragment()
            fragment.slideTitle = slideTitle
            fragment.slideSummary = slideSummary
            fragment.slideBackgroundColor = slideBackgroundColor
            return fragment
        }

    }
}