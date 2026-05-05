package org.wheatgenetics.coordinate.brapi.dialogs

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.phenoapps.brapi.ui.BrapiStepperAccountForm
import org.phenoapps.brapi.ui.PhenoBrapiTheme
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.brapi.BrapiAuthActivity
import org.wheatgenetics.coordinate.brapi.BrapiAuthenticator
import org.wheatgenetics.coordinate.utilities.BrapiAccountHelper
import javax.inject.Inject

@AndroidEntryPoint
class BrapiStepperAccountDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "BrapiStepperAccountDialog"
        private const val ARG_AUTH_RESPONSE = "auth_response"

        fun newInstance(authResponse: AccountAuthenticatorResponse? = null) =
            BrapiStepperAccountDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_AUTH_RESPONSE, authResponse)
                }
            }
    }

    @Inject
    lateinit var accountHelper: BrapiAccountHelper

    private var authResponse: AccountAuthenticatorResponse? = null
    private var accountWasNew = false
    private var uiState by mutableStateOf(org.phenoapps.brapi.ui.BrapiAccountUiState())

    private val authLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                authResponse?.onResult(Bundle().apply {
                    putString(AccountManager.KEY_ACCOUNT_NAME, uiState.url)
                    putString(AccountManager.KEY_ACCOUNT_TYPE, BrapiAuthenticator.ACCOUNT_TYPE)
                })
                dismiss()
                if (authResponse != null) {
                    activity?.setResult(Activity.RESULT_OK)
                    activity?.finish()
                }
            } else {
                handleAuthFailed()
            }
        }

    private val scanBaseUrlLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val scanned = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
                ?.contents
                ?.takeIf { it.isNotEmpty() } ?: return@registerForActivityResult
            uiState = uiState.withUrlUpdate(scanned)
        }

    private val scanConfigLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val scanned = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
                ?.contents
                ?.takeIf { it.isNotEmpty() } ?: return@registerForActivityResult

            val config = parseBrapiConfig(scanned)
            uiState = if (config != null) {
                uiState.withConfig(config).copy(currentStep = 2)
            } else {
                uiState.withUrlUpdate(scanned).copy(currentStep = 1)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, org.phenoapps.brapi.R.style.PhenoBrapiComposeDialog)
        authResponse = arguments?.getParcelable(ARG_AUTH_RESPONSE)
        if (savedInstanceState == null) {
            uiState = defaultBrapiAccountState(requireContext())
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
                BrapiStepperAccountForm(
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
                    onScanBaseUrl = { scan(scanBaseUrlLauncher) },
                    onScanConfig = { scan(scanConfigLauncher) },
                    onNext = { onNext() },
                    onBack = { uiState = uiState.copy(currentStep = (uiState.currentStep - 1).coerceAtLeast(0)) },
                    onCancel = { cancelDialog() },
                    onAuthorize = { authorize() },
                )
            }
        }
    }

    private fun onNext() {
        when (uiState.currentStep) {
            0 -> uiState = uiState.copy(currentStep = 1)
            1 -> {
                val normalized = runCatching { accountHelper.normalizeUrl(uiState.url) }.getOrDefault("")
                if (normalized.isEmpty() || !isValidBrapiUrl(normalized)) {
                    Toast.makeText(requireContext(), R.string.brapi_invalid_url, Toast.LENGTH_LONG).show()
                    return
                }
                uiState = uiState.copy(url = normalized, currentStep = 2)
                fetchDisplayName(normalized)
            }
        }
    }

    private fun authorize() {
        val url = runCatching { accountHelper.normalizeUrl(uiState.url) }.getOrDefault("")
        if (url.isEmpty() || !isValidBrapiUrl(url)) {
            Toast.makeText(requireContext(), R.string.brapi_invalid_url, Toast.LENGTH_LONG).show()
            return
        }

        accountWasNew = accountHelper.getAccountByUrl(url) == null
        val displayName = uiState.displayName.trim().ifEmpty { url }
        accountHelper.addAccountConfig(
            serverUrl = url,
            displayName = displayName,
            oidcUrl = uiState.oidcUrl.trim(),
            oidcFlow = uiState.oidcFlow,
            oidcClientId = uiState.oidcClientId.trim(),
            oidcScope = uiState.oidcScope.trim(),
            brapiVersion = uiState.brapiVersion,
        )
        accountHelper.setActiveAccount(url)

        authLauncher.launch(
            Intent(requireContext(), BrapiAuthActivity::class.java).apply {
                putExtra(BrapiAuthActivity.EXTRA_SERVER_URL, url)
                putExtra(BrapiAuthActivity.EXTRA_OIDC_URL, uiState.oidcUrl.trim())
                putExtra(BrapiAuthActivity.EXTRA_OIDC_FLOW, uiState.oidcFlow)
                putExtra(BrapiAuthActivity.EXTRA_OIDC_CLIENT_ID, uiState.oidcClientId.trim())
                putExtra(BrapiAuthActivity.EXTRA_OIDC_SCOPE, uiState.oidcScope.trim())
                putExtra(BrapiAuthActivity.EXTRA_BRAPI_VERSION, uiState.brapiVersion)
                if (authResponse != null) {
                    putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, authResponse)
                }
            },
        )
    }

    private fun handleAuthFailed() {
        if (!accountWasNew) {
            cancelDialog()
            return
        }

        val serverName = uiState.displayName.trim().ifEmpty { uiState.url }
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.brapi_auth_failed_keep_title)
            .setMessage(getString(R.string.brapi_auth_failed_keep_message, serverName))
            .setPositiveButton(R.string.brapi_auth_failed_keep) { _, _ ->
                authResponse?.onError(AccountManager.ERROR_CODE_CANCELED, "cancelled")
                dismiss()
                if (authResponse != null) activity?.finish()
            }
            .setNegativeButton(R.string.brapi_auth_failed_remove) { _, _ ->
                accountHelper.removeAccount(uiState.url)
                authResponse?.onError(AccountManager.ERROR_CODE_CANCELED, "cancelled")
                dismiss()
                if (authResponse != null) activity?.finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun cancelDialog() {
        authResponse?.onError(AccountManager.ERROR_CODE_CANCELED, "cancelled")
        dismiss()
        if (authResponse != null) {
            activity?.setResult(Activity.RESULT_CANCELED)
            activity?.finish()
        }
    }

    private fun scan(launcher: ActivityResultLauncher<Intent>) {
        IntentIntegrator(requireActivity()).apply {
            setOrientationLocked(false)
            setPrompt(getString(org.phenoapps.brapi.R.string.pheno_brapi_dialog_scan))
            setBeepEnabled(true)
        }.createScanIntent().also(launcher::launch)
    }

    private fun fetchDisplayName(baseUrl: String) {
        lifecycleScope.launch {
            uiState = uiState.copy(isFetchingDisplayName = true)
            val displayText = withContext(Dispatchers.IO) {
                runCatching {
                    val serverInfoUrl = baseUrl.trimEnd('/') + "/brapi/v2/serverinfo"
                    val conn = java.net.URL(serverInfoUrl).openConnection() as java.net.HttpURLConnection
                    conn.connectTimeout = 5000
                    conn.readTimeout = 5000
                    if (conn.responseCode == 200) {
                        val body = conn.inputStream.bufferedReader().readText()
                        org.json.JSONObject(body)
                            .optJSONObject("result")
                            ?.optString("serverName")
                            ?.takeIf { it.isNotEmpty() }
                    } else {
                        null
                    }
                }.getOrNull() ?: runCatching { java.net.URL(baseUrl).host }.getOrDefault("")
            }
            uiState = uiState.copy(
                displayName = displayText.takeIf { it.isNotEmpty() } ?: uiState.displayName,
                isFetchingDisplayName = false,
            )
        }
    }
}
