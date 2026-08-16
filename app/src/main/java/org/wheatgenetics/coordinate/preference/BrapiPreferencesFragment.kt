package org.wheatgenetics.coordinate.preference

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.phenoapps.brapi.BrapiAccountConstants
import org.phenoapps.brapi.config.BrapiAccountInfo
import org.phenoapps.brapi.ui.BrapiAccountConfig
import org.phenoapps.brapi.ui.BrapiServerCardPreference as ProviderBrapiServerCardPreference
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.brapi.BrapiAuthActivity
import org.wheatgenetics.coordinate.brapi.BrapiAuthenticator
import org.wheatgenetics.coordinate.brapi.dialogs.BrapiManualAccountDialogFragment
import org.wheatgenetics.coordinate.brapi.dialogs.BrapiStepperAccountDialogFragment
import org.wheatgenetics.coordinate.utilities.BrapiAccountHelper
import javax.inject.Inject

@AndroidEntryPoint
class BrapiPreferencesFragment(
    private val searchResult: com.bytehamster.lib.preferencesearch.SearchPreferenceResult? = null,
) : BasePreferenceFragment() {

    @Inject
    lateinit var accountHelper: BrapiAccountHelper

    private val authLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            populateServerCards()
        }
    }

    private val accountChooserLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        handleAccountChooserResult(result.resultCode, result.data)
    }

    private var pendingChooseAccount: Account? = null

    private var activeServerCategory: PreferenceCategory? = null
    private var availableServersCategory: PreferenceCategory? = null
    private var sharedAccountsCategory: PreferenceCategory? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_brapi, rootKey)
        setToolbar(getString(R.string.pref_brapi_title))

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        activeServerCategory = findPreference("brapi_active_server_category")
        availableServersCategory = findPreference("brapi_available_servers_category")
        sharedAccountsCategory = findPreference("brapi_shared_accounts_category")

        val enabledPref = findPreference<CheckBoxPreference>(GeneralKeys.BRAPI_ENABLED)
        val isEnabled = enabledPref?.isChecked ?: false

        updateSectionsVisibility(isEnabled)

        enabledPref?.setOnPreferenceChangeListener { _, newValue ->
            val enabled = newValue as Boolean
            updateSectionsVisibility(enabled)
            updateHiddenTemplates(enabled)
            if (!enabled) {
                prefs.edit()
                    .remove(GeneralKeys.BRAPI_TOKEN)
                    .remove(GeneralKeys.BRAPI_ID_TOKEN)
                    .apply()
            }
            true
        }

        findPreference<Preference>("brapi_add_account")?.setOnPreferenceClickListener {
            BrapiStepperAccountDialogFragment.newInstance()
                .show(parentFragmentManager, BrapiStepperAccountDialogFragment.TAG)
            true
        }

        findPreference<Preference>("brapi_shared_servers")?.setOnPreferenceClickListener {
            pendingChooseAccount = null
            accountChooserLauncher.launch(accountHelper.buildChooseAccountIntent())
            true
        }

        parentFragmentManager.setFragmentResultListener(
            BrapiManualAccountDialogFragment.REQUEST_KEY_EDIT_SAVED,
            this,
        ) { _, _ -> populateServerCards() }

        // Run migration for users upgrading from SharedPreferences storage
        accountHelper.migrateFromPrefsIfNeeded()
    }

    override fun onResume() {
        super.onResume()
        accountHelper.refreshOwnedAccountVisibility()
        accountHelper.pruneStaleGrants()
        populateServerCards()
    }

    private fun updateSectionsVisibility(enabled: Boolean) {
        findPreference<Preference>("brapi_shared_servers")?.isVisible = enabled
        activeServerCategory?.isVisible = enabled
        availableServersCategory?.isVisible = enabled
        if (enabled) populateServerCards() else sharedAccountsCategory?.isVisible = false
    }

    private fun populateServerCards() {
        val activeCategory = activeServerCategory ?: return
        val availableCategory = availableServersCategory ?: return
        val sharedCategory = sharedAccountsCategory ?: return

        activeCategory.removeAll()
        availableCategory.removeAll()
        sharedCategory.removeAll()

        val allAccounts = accountHelper.getAllAccounts()
        val activeUrl = accountHelper.findAccount()?.let { accountHelper.accountInfo(it)?.serverUrl }

        // An account owned by another app keeps its config out of reach of getUserData, so every
        // description here comes from accountInfo(), which falls back to that app's config provider.
        val (ownedAccounts, foreignAccounts) = allAccounts.partition { accountHelper.isOwnAccount(it) }

        for (account in ownedAccounts) {
            val info = accountHelper.accountInfoOrEmpty(account)
            val isActive = info.serverUrl == activeUrl
            val card = buildCard(account, info, isActive, ownerLabel = null)
            if (isActive) activeCategory.addPreference(card)
            else availableCategory.addPreference(card)
        }

        for (account in foreignAccounts) {
            val info = accountHelper.accountInfoOrEmpty(account)
            val ownerLabel = getString(
                org.phenoapps.brapi.R.string.pheno_brapi_shared_account_from,
                BrapiAccountConstants.displayNameForPackage(info.ownerPackage),
            )
            val isActive = info.serverUrl == activeUrl
            val card = buildCard(account, info, isActive = isActive, ownerLabel = ownerLabel)
            if (isActive) activeCategory.addPreference(card)
            else sharedCategory.addPreference(card)
        }

        sharedCategory.isVisible = sharedCategory.preferenceCount > 0
        activeCategory.isVisible = activeCategory.preferenceCount > 0
        availableCategory.isVisible = availableCategory.preferenceCount > 0
    }

    private fun buildCard(
        account: Account,
        info: BrapiAccountInfo,
        isActive: Boolean,
        ownerLabel: String?,
    ): ProviderBrapiServerCardPreference {
        return ProviderBrapiServerCardPreference(requireContext()).apply {
            this.account = account
            this.isActive = isActive
            this.ownerLabel = ownerLabel
            this.displayName = info.label
            this.serverUrl = info.serverUrl
            // A shared account's token lives in the owning app, out of reach of peekAuthToken, so
            // its sign-in state comes from what that app published about it.
            this.hasToken = if (accountHelper.isOwnAccount(account)) {
                !accountHelper.peekTokenForAccount(account).isNullOrEmpty()
            } else {
                info.hasToken
            }
            key = "brapi_card_${account.name}"
            isExpanded = isActive

            val isOwn = accountHelper.isOwnAccount(account)

            onEnable = { acct -> enableAccount(acct) }
            onSwitchServer = { acct -> showSwitchServerDialog(acct) }
            onShareSettings = { acct -> shareAccountSettings(acct) }
            onCheckCompatibility = { acct -> checkServerCompatibility(acct) }

            // No local sign-in, edit or removal for a shared account. Authorizing here would run
            // Coordinate's own OAuth and store the result against a new Coordinate account,
            // leaving the same server listed twice in the system account settings. Its token is
            // borrowed from its owner instead.
            onAuthorize = if (isOwn) ({ acct -> authorizeAccount(acct) }) else null
            onEdit = if (isOwn) ({ acct -> editAccount(acct) }) else null
            onRemove = if (isOwn) ({ acct -> showManageServerDialog(acct, offerLogout = false) }) else null
            // Logging out stays available on a shared account: it drops the borrowed token from
            // this app's mirrors without touching the owner's account.
            onLogOut = { acct -> showManageServerDialog(acct, offerLogout = true) }
        }
    }

    private fun showSwitchServerDialog(account: Account) {
        AlertDialog.Builder(requireContext())
            .setTitle(org.phenoapps.brapi.R.string.pheno_brapi_switch_server_title)
            .setMessage(R.string.brapi_switch_server_unauthenticated_message)
            .setPositiveButton(R.string.brapi_dialog_yes) { _, _ -> authorizeAccount(account) }
            .setNegativeButton(R.string.brapi_dialog_no) { _, _ -> enableAccount(account) }
            .show()
    }

    private fun enableAccount(account: Account) {
        if (!accountHelper.canUseToken(account)) {
            pendingChooseAccount = account
            accountChooserLauncher.launch(accountHelper.buildChooseAccountIntent(account))
            return
        }

        activateAccount(account)
    }

    private fun activateAccount(account: Account) {
        val url = accountHelper.serverUrlOf(account)
        accountHelper.setActiveAccount(url)

        if (!accountHelper.isOwnAccount(account)) {
            // Selecting a shared server is not enough to use it: its token belongs to the app that
            // owns the account, so fetch it now and mirror it locally. Without this the server
            // looks signed out no matter how many times it is selected.
            accountHelper.borrowToken(account, activity) { token ->
                if (token == null) {
                    Toast.makeText(
                        requireContext(),
                        R.string.brapi_shared_account_not_signed_in,
                        Toast.LENGTH_LONG,
                    ).show()
                }
                populateServerCards()
            }
            return
        }

        populateServerCards()
    }

    private fun handleAccountChooserResult(resultCode: Int, data: Intent?) {
        val pending = pendingChooseAccount
        pendingChooseAccount = null

        if (resultCode != Activity.RESULT_OK || data == null) return

        val accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME) ?: return
        val accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE)
            ?: BrapiAuthenticator.accountType(requireContext())
        // Any PhenoApp's type, not just this app's — picking a sibling's account here is how the
        // user grants Coordinate access to a server that app owns.
        if (!BrapiAuthenticator.isBrapiAccountType(accountType)) return

        val selected = Account(accountName, accountType)
        val accountToUse = pending ?: selected
        if (pending != null && (pending.name != selected.name || pending.type != selected.type)) return
        if (!accountHelper.canAccessAccount(accountToUse)) return

        accountHelper.grantSelectedAccount(accountToUse)
        if (accountHelper.canUseToken(accountToUse)) {
            activateAccount(accountToUse)
        }
    }

    private fun authorizeAccount(account: Account) {
        val info = accountHelper.accountInfoOrEmpty(account)
        val url = info.serverUrl.ifEmpty { account.name }
        accountHelper.setActiveAccount(url)
        authLauncher.launch(
            Intent(requireContext(), BrapiAuthActivity::class.java).apply {
                putExtra(BrapiAuthActivity.EXTRA_SERVER_URL, url)
                putExtra(BrapiAuthActivity.EXTRA_OIDC_URL, info.oidcUrl)
                putExtra(BrapiAuthActivity.EXTRA_OIDC_FLOW, info.oidcFlow)
                putExtra(BrapiAuthActivity.EXTRA_OIDC_CLIENT_ID, info.oidcClientId)
                putExtra(BrapiAuthActivity.EXTRA_OIDC_SCOPE, info.oidcScope)
                putExtra(BrapiAuthActivity.EXTRA_BRAPI_VERSION, info.brapiVersion)
            },
        )
    }

    /**
     * Offers logout, and removal only when this app owns the account.
     *
     * Removal is the owner's to do: [BrapiAccountHelper.removeAccount] only touches accounts this
     * app owns, so offering it for a shared server would promise something it cannot deliver.
     */
    private fun showManageServerDialog(account: Account, offerLogout: Boolean) {
        val info = accountHelper.accountInfoOrEmpty(account)
        val url = info.serverUrl.ifEmpty { account.name }
        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.brapi_logout_manage_title)
            .setMessage(getString(R.string.brapi_logout_manage_message, info.label))
            .setNegativeButton(android.R.string.cancel, null)

        if (accountHelper.isOwnAccount(account)) {
            builder.setPositiveButton(R.string.brapi_logout_and_remove) { _, _ ->
                accountHelper.clearToken(url)
                accountHelper.removeAccount(url)
                Toast.makeText(requireContext(), R.string.pref_brapi_token_revoked, Toast.LENGTH_SHORT).show()
                populateServerCards()
            }
        }
        if (offerLogout) {
            builder.setNeutralButton(R.string.brapi_logout_only) { _, _ ->
                accountHelper.clearToken(url)
                Toast.makeText(requireContext(), R.string.pref_brapi_token_revoked, Toast.LENGTH_SHORT).show()
                populateServerCards()
            }
        }
        builder.show()
    }

    private fun editAccount(account: Account) {
        BrapiManualAccountDialogFragment.newInstance(account.toBrapiConfig())
            .show(parentFragmentManager, BrapiManualAccountDialogFragment.TAG)
    }

    private fun shareAccountSettings(account: Account) {
        val config = account.toBrapiConfig()
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val jsonConfig = JSONObject().apply {
            put("url", config.url)
            put("name", config.name)
            put("version", config.version)
            put("authFlow", config.authFlow)
            put("oidcUrl", config.oidcUrl)
            put("clientId", config.clientId)
            put("scope", config.scope)
            put("pageSize", prefs.getString(GeneralKeys.BRAPI_PAGE_SIZE, "50"))
            put("serverTimeoutMilli", prefs.getString(GeneralKeys.BRAPI_TIMEOUT, "120"))
        }.toString()

        val size = (resources.displayMetrics.widthPixels * 0.8f).toInt().coerceAtLeast(320)
        val matrix = MultiFormatWriter().encode(jsonConfig, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.brapi_barcode_config_dialog_title)
            .setView(ImageView(requireContext()).apply {
                setImageBitmap(bitmap)
                adjustViewBounds = true
            })
            .setPositiveButton(R.string.brapi_dialog_close, null)
            .show()
    }

    private fun checkServerCompatibility(account: Account) {
        val url = accountHelper.serverUrlOf(account)
        lifecycleScope.launch {
            val message = withContext(Dispatchers.IO) {
                runCatching {
                    val serverInfoUrl = url.trimEnd('/') + "/brapi/v2/serverinfo"
                    val conn = java.net.URL(serverInfoUrl).openConnection() as java.net.HttpURLConnection
                    conn.connectTimeout = 5000
                    conn.readTimeout = 5000
                    if (conn.responseCode != 200) return@runCatching null
                    val result = JSONObject(conn.inputStream.bufferedReader().readText())
                        .optJSONObject("result")
                    val name = result?.optString("serverName")?.takeIf { it.isNotEmpty() } ?: url
                    val calls = result?.optJSONArray("calls")?.length()
                    if (calls != null) "$name\n$calls BrAPI calls advertised" else name
                }.getOrNull()
            }
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.brapi_server_compatibility_title)
                .setMessage(message ?: getString(R.string.brapi_server_compatibility_unavailable))
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }

    private fun Account.toBrapiConfig(): BrapiAccountConfig =
        accountHelper.accountInfoOrEmpty(this).let { info ->
            BrapiAccountConfig(
                url = info.serverUrl,
                name = info.displayName,
                version = info.brapiVersion,
                authFlow = info.oidcFlow,
                oidcUrl = info.oidcUrl,
                clientId = info.oidcClientId,
                scope = info.oidcScope,
            )
        }

    private fun updateHiddenTemplates(brapiEnabled: Boolean) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val brapiCode = "5"
        val current = prefs.getString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, "") ?: ""
        val codes = current.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toMutableList()
        if (brapiEnabled) codes.remove(brapiCode)
        else if (!codes.contains(brapiCode)) codes.add(brapiCode)
        prefs.edit().putString(GeneralKeys.HIDDEN_BUILTIN_TEMPLATES, codes.joinToString(",")).apply()
    }
}
