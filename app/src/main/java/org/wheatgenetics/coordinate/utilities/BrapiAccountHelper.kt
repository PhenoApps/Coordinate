package org.wheatgenetics.coordinate.utilities

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import org.phenoapps.brapi.account.BrapiAccountRepository
import org.phenoapps.brapi.account.BrapiPreferenceKeys
import org.wheatgenetics.coordinate.preference.GeneralKeys
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrapiAccountHelper @Inject constructor(
    @param:ApplicationContext context: Context,
    preferences: SharedPreferences,
) : BrapiAccountRepository(
    context = context,
    preferences = preferences,
    preferenceKeys = BrapiPreferenceKeys(
        enabled = GeneralKeys.BRAPI_ENABLED,
        baseUrl = GeneralKeys.BRAPI_BASE_URL,
        displayName = GeneralKeys.BRAPI_DISPLAY_NAME,
        accessToken = GeneralKeys.BRAPI_TOKEN,
        idToken = GeneralKeys.BRAPI_ID_TOKEN,
        // BrapiClientFactory and the re-authorization flow still build from these mirrors, so they
        // have to follow whichever account is active. Declaring them here lets the repository keep
        // them in step, instead of the preferences screen mirroring the account by hand.
        oidcUrl = GeneralKeys.BRAPI_OIDC_URL,
        oidcFlow = GeneralKeys.BRAPI_OIDC_FLOW,
        oidcClientId = GeneralKeys.BRAPI_OIDC_CLIENT_ID,
        oidcScope = GeneralKeys.BRAPI_OIDC_SCOPE,
        brapiVersion = GeneralKeys.BRAPI_VERSION,
    ),
)
