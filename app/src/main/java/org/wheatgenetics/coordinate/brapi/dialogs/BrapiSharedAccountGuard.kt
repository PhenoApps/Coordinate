package org.wheatgenetics.coordinate.brapi.dialogs

import android.widget.Toast
import androidx.fragment.app.Fragment
import org.phenoapps.brapi.BrapiAccountConstants
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.utilities.BrapiAccountHelper

/**
 * Refuses a server another app already shares, naming that app.
 *
 * Adding it here would create a second account for the same server, which is what lists one server
 * twice in the system account settings; the shared one is already usable from the server list.
 * Shared between the stepper and manual dialogs, which have no common base class.
 *
 * @return true when the caller should stop.
 */
internal fun Fragment.rejectedAsAlreadyShared(
    accountHelper: BrapiAccountHelper,
    url: String,
): Boolean {
    val shared = accountHelper.sharedAccountForUrl(url) ?: return false
    val owner = BrapiAccountConstants.displayNameForPackage(accountHelper.ownerPackageOf(shared))
    Toast.makeText(
        requireContext(),
        getString(R.string.brapi_add_account_already_shared, owner),
        Toast.LENGTH_LONG,
    ).show()
    return true
}
