package org.wheatgenetics.coordinate.brapi.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.phenoapps.brapi.ui.BrapiAccountConfig
import org.phenoapps.brapi.ui.BrapiManualAccountForm
import org.phenoapps.brapi.ui.PhenoBrapiTheme
import org.phenoapps.brapi.ui.defaultBrapiAccountState
import org.phenoapps.brapi.ui.isValidBrapiUrl
import org.phenoapps.brapi.ui.withConfig
import org.phenoapps.brapi.ui.withUrlUpdate
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.utilities.BrapiAccountHelper
import javax.inject.Inject

@AndroidEntryPoint
class BrapiManualAccountDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "BrapiManualAccountDialog"
        const val REQUEST_KEY_EDIT_SAVED = "brapi_edit_account_saved"

        private const val ARG_PREFILL_URL = "prefill_url"
        private const val ARG_PREFILL_NAME = "prefill_name"
        private const val ARG_PREFILL_VERSION = "prefill_version"
        private const val ARG_PREFILL_FLOW = "prefill_flow"
        private const val ARG_PREFILL_OIDC_URL = "prefill_oidc_url"
        private const val ARG_PREFILL_CLIENT_ID = "prefill_client_id"
        private const val ARG_PREFILL_SCOPE = "prefill_scope"

        fun newInstance(config: BrapiAccountConfig): BrapiManualAccountDialogFragment =
            BrapiManualAccountDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PREFILL_URL, config.url)
                    putString(ARG_PREFILL_NAME, config.name)
                    putString(ARG_PREFILL_VERSION, config.version)
                    putString(ARG_PREFILL_FLOW, config.authFlow)
                    putString(ARG_PREFILL_OIDC_URL, config.oidcUrl)
                    putString(ARG_PREFILL_CLIENT_ID, config.clientId)
                    putString(ARG_PREFILL_SCOPE, config.scope)
                }
            }
    }

    @Inject
    lateinit var accountHelper: BrapiAccountHelper

    private var uiState by mutableStateOf(org.phenoapps.brapi.ui.BrapiAccountUiState())
    private var originalUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, org.phenoapps.brapi.R.style.PhenoBrapiComposeDialog)
        if (savedInstanceState == null) {
            val config = BrapiAccountConfig(
                url = arguments?.getString(ARG_PREFILL_URL),
                name = arguments?.getString(ARG_PREFILL_NAME),
                version = arguments?.getString(ARG_PREFILL_VERSION),
                authFlow = arguments?.getString(ARG_PREFILL_FLOW),
                oidcUrl = arguments?.getString(ARG_PREFILL_OIDC_URL),
                clientId = arguments?.getString(ARG_PREFILL_CLIENT_ID),
                scope = arguments?.getString(ARG_PREFILL_SCOPE),
            )
            originalUrl = config.url
            uiState = defaultBrapiAccountState(
                requireContext(),
                getString(R.string.brapi_oidc_clientid_default),
            ).withConfig(config)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
            )
            WindowCompat.setDecorFitsSystemWindows(this, false)
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindowOrReleasedFromPool)
        setContent {
            PhenoBrapiTheme {
                BrapiManualAccountForm(
                    title = getString(org.phenoapps.brapi.R.string.pheno_brapi_edit_account_title),
                    uiState = uiState,
                    onUrlChange = { uiState = uiState.withUrlUpdate(it) },
                    onDisplayNameChange = { uiState = uiState.copy(displayName = it) },
                    onOidcUrlChange = { url, isUserEdit ->
                        uiState = uiState.copy(
                            oidcUrl = url,
                            oidcUrlExplicitlySet = if (isUserEdit) true else uiState.oidcUrlExplicitlySet,
                        )
                    },
                    onOidcClientIdChange = { uiState = uiState.copy(oidcClientId = it) },
                    onOidcScopeChange = { uiState = uiState.copy(oidcScope = it) },
                    onOidcFlowChange = { uiState = uiState.copy(oidcFlow = it) },
                    onBrapiVersionChange = { uiState = uiState.copy(brapiVersion = it) },
                    isEditMode = true,
                    onCancel = { dismiss() },
                    onConfirm = { saveEdit() },
                )
            }
        }
    }

    private fun saveEdit() {
        val url = runCatching { accountHelper.normalizeUrl(uiState.url) }.getOrDefault("")
        if (url.isEmpty() || !isValidBrapiUrl(url)) {
            Toast.makeText(
                requireContext(),
                org.phenoapps.brapi.R.string.pheno_brapi_invalid_url,
                Toast.LENGTH_LONG,
            ).show()
            return
        }
        // Only an edit that repoints this account at a server a sibling shares is a problem; an
        // edit that leaves the URL alone is matched to the existing account by originalServerUrl.
        if (url != originalUrl && rejectedAsAlreadyShared(accountHelper, url)) return

        accountHelper.addAccountConfig(
            serverUrl = url,
            displayName = uiState.displayName.trim().ifEmpty { url },
            oidcUrl = uiState.oidcUrl.trim(),
            oidcFlow = uiState.oidcFlow,
            oidcClientId = uiState.oidcClientId.trim(),
            oidcScope = uiState.oidcScope.trim(),
            brapiVersion = uiState.brapiVersion,
            originalServerUrl = originalUrl,
        ) ?: return
        parentFragmentManager.setFragmentResult(REQUEST_KEY_EDIT_SAVED, Bundle.EMPTY)
        dismiss()
    }
}
