package org.wheatgenetics.coordinate.brapi.service

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import org.brapi.client.v2.BrAPIClient
import org.brapi.client.v2.model.exceptions.ApiException
import org.wheatgenetics.coordinate.preference.GeneralKeys

object BrapiClientFactory {

    private const val TAG = "BrapiHttp"

    fun buildClient(context: Context): BrAPIClient {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val baseUrl = prefs.getString(GeneralKeys.BRAPI_BASE_URL, "") ?: ""
        val timeoutSec = prefs.getString(GeneralKeys.BRAPI_TIMEOUT, "120")?.toIntOrNull() ?: 120

        Log.d(TAG, "buildClient: baseUrl=$baseUrl, timeoutSec=$timeoutSec")

        val client = BrAPIClient(baseUrl, timeoutSec * 1000)

        // Attach a logging interceptor so every HTTP call is visible in Logcat
        val loggingInterceptor = Interceptor { chain ->
            val request = chain.request()
            Log.d(TAG, "--> ${request.method} ${request.url}")
            request.headers.names().forEach { name ->
                if (!name.equals("Authorization", ignoreCase = true)) {
                    Log.d(TAG, "    $name: ${request.header(name)}")
                }
            }
            val response: Response = chain.proceed(request)
            Log.d(TAG, "<-- ${response.code} ${response.message} ${request.url}")
            response
        }
        val instrumentedHttpClient = client.httpClient.newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
        client.setHttpClient(instrumentedHttpClient)

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
