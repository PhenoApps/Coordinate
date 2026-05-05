package org.wheatgenetics.coordinate.brapi

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.phenoapps.brapi.BrapiAccountConstants
import org.wheatgenetics.coordinate.brapi.dialogs.BrapiStepperAccountDialogFragment

/**
 * Thin host activity for the BrAPI add-account dialog.
 * Launched by BrapiAuthenticator.addAccount() (from system AccountManager) or directly
 * from BrapiPreferencesFragment when the user taps "Add Account".
 */
@AndroidEntryPoint
class BrapiAddAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getBooleanExtra(BrapiAccountConstants.EXTRA_SHOW_IN_APP_ADDER_TOAST, false)) {
            val message = getString(org.phenoapps.brapi.R.string.pheno_brapi_add_account_in_app_only)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            intent.getParcelableExtra<AccountAuthenticatorResponse>(
                AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE
            )?.onError(AccountManager.ERROR_CODE_CANCELED, message)
            finish()
            return
        }

        if (savedInstanceState == null) {
            val authResponse = intent.getParcelableExtra<android.accounts.AccountAuthenticatorResponse>(
                AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE
            )
            val fragment = BrapiStepperAccountDialogFragment.newInstance(authResponse)
            fragment.show(supportFragmentManager, BrapiStepperAccountDialogFragment.TAG)
        }
    }
}
