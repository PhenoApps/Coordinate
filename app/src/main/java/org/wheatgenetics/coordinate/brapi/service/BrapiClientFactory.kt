package org.wheatgenetics.coordinate.brapi.service

import android.content.Context
import androidx.preference.PreferenceManager
import org.brapi.client.v2.BrAPIClient
import org.brapi.client.v2.model.exceptions.ApiException
import org.wheatgenetics.coordinate.preference.GeneralKeys

object BrapiClientFactory {

    fun buildClient(context: Context): BrAPIClient {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val baseUrl = prefs.getString(GeneralKeys.BRAPI_BASE_URL, "") ?: ""
        val timeoutSec = prefs.getString(GeneralKeys.BRAPI_TIMEOUT, "120")?.toIntOrNull() ?: 120

        val client = BrAPIClient(baseUrl, timeoutSec * 1000)
        try {
            client.authenticate { _ ->
                prefs.getString(GeneralKeys.BRAPI_TOKEN, null)
            }
        } catch (e: ApiException) {
            // Authentication setup is best-effort; continue without token if it fails
        }
        return client
    }
}
