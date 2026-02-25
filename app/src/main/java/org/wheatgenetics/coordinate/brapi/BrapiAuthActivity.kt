package org.wheatgenetics.coordinate.brapi

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenResponse
import org.wheatgenetics.coordinate.BackActivity
import org.wheatgenetics.coordinate.R
import org.wheatgenetics.coordinate.preference.GeneralKeys
import org.wheatgenetics.coordinate.utils.InsetHandler

class BrapiAuthActivity : BackActivity() {

    companion object {
        const val REDIRECT_URI = "coordinate://app/auth"
        private const val TAG = "BrapiAuthActivity"
    }

    private lateinit var authUtil: OpenAuthConfigUtil
    private var activityStarting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brapi_auth)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.brapi_auth_title)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        val rootView = findViewById<android.view.View>(android.R.id.content)
        InsetHandler.setupStandardInsets(rootView, toolbar)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        authUtil = OpenAuthConfigUtil(this, prefs)

        activityStarting = true

        // Start auth only when not returning from deep link
        if (intent?.data == null) {
            val flow = prefs.getString(GeneralKeys.BRAPI_OIDC_FLOW, "") ?: ""
            if (flow == getString(R.string.pref_brapi_oidc_flow_implicit)) {
                authorizeBrAPIImplicit(prefs)
            } else {
                authorizeBrAPICode(prefs)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()

        if (activityStarting) {
            activityStarting = false
            return
        }

        val ex = AuthorizationException.fromIntent(intent)
        val data = intent?.data

        when {
            data != null -> checkBrapiAuth(data)
            ex != null -> authError(ex)
            else -> {
                intent?.data = null
                finish()
            }
        }
    }

    private fun authorizeBrAPIImplicit(prefs: android.content.SharedPreferences) {
        prefs.edit().putString(GeneralKeys.BRAPI_TOKEN, null).apply()

        val clientId = prefs.getString(GeneralKeys.BRAPI_OIDC_CLIENT_ID, "coordinate") ?: "coordinate"
        val scope = prefs.getString(GeneralKeys.BRAPI_OIDC_SCOPE, "") ?: ""
        val redirectUri = Uri.parse("https://phenoapps.org/coordinate")

        try {
            authUtil.getAuthServiceConfiguration { config, err ->
                if (err != null || config == null) {
                    Log.e(TAG, "Failed to fetch OIDC config", err)
                    authError(err ?: Exception("No config"))
                    return@getAuthServiceConfiguration
                }
                try {
                    requestAuthorization(config, clientId, ResponseTypeValues.TOKEN, redirectUri, scope)
                } catch (e: Exception) {
                    authError(e)
                }
            }
        } catch (e: Exception) {
            authError(e)
        }
    }

    private fun authorizeBrAPICode(prefs: android.content.SharedPreferences) {
        prefs.edit().putString(GeneralKeys.BRAPI_TOKEN, null).apply()

        val clientId = prefs.getString(GeneralKeys.BRAPI_OIDC_CLIENT_ID, "coordinate") ?: "coordinate"
        val scope = prefs.getString(GeneralKeys.BRAPI_OIDC_SCOPE, "") ?: ""
        val redirectUri = Uri.parse(REDIRECT_URI)

        try {
            authUtil.getAuthServiceConfiguration { config, err ->
                if (err != null || config == null) {
                    Log.e(TAG, "Failed to fetch OIDC config", err)
                    authError(err ?: Exception("No config"))
                    return@getAuthServiceConfiguration
                }
                try {
                    requestAuthorization(config, clientId, ResponseTypeValues.CODE, redirectUri, scope)
                } catch (e: Exception) {
                    authError(e)
                }
            }
        } catch (e: Exception) {
            authError(e)
        }
    }

    private fun requestAuthorization(
        serviceConfig: net.openid.appauth.AuthorizationServiceConfiguration,
        clientId: String,
        responseType: String,
        redirectUri: Uri,
        scope: String,
    ) {
        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            responseType,
            redirectUri,
        )

        if (scope.trim().isNotEmpty()) {
            authRequestBuilder.setScope("$scope openid")
        } else {
            authRequestBuilder.setScopes("openid")
        }

        val authRequest = authRequestBuilder.setPrompt("login").build()
        val authService = getAuthorizationService()

        val responseIntent = Intent(this, BrapiAuthActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        authService.performAuthorizationRequest(
            authRequest,
            PendingIntent.getActivity(this, 0, responseIntent, PendingIntent.FLAG_MUTABLE),
        )
    }

    private fun getAuthorizationService(): AuthorizationService {
        val builder = AppAuthConfiguration.Builder()
        builder.setConnectionBuilder(authUtil.getConnectionBuilder())
        return AuthorizationService(this, builder.build())
    }

    fun checkBrapiAuth(data: Uri) {
        val authService = getAuthorizationService()
        val ex = AuthorizationException.fromIntent(intent)
        val response = AuthorizationResponse.fromIntent(intent)

        if (ex != null) {
            authError(ex)
            return
        }

        if (response?.authorizationCode != null) {
            authService.performTokenRequest(response.createTokenExchangeRequest()) { tokenResponse: TokenResponse?, tokenEx: AuthorizationException? ->
                if (tokenResponse?.accessToken != null) {
                    authSuccess(tokenResponse.accessToken!!, tokenResponse.idToken)
                } else {
                    authError(tokenEx)
                }
            }
            return
        }

        if (response?.accessToken != null) {
            authSuccess(response.accessToken!!, null)
            return
        }

        // Fallback: parse access_token from fragment
        val modifiedData = Uri.parse(data.toString().replaceFirst("#", "?"))
        var token = modifiedData.getQueryParameter("access_token")
        if (token == null) {
            authError(null)
            return
        }
        if (token.startsWith("Bearer ")) {
            token = token.removePrefix("Bearer ")
        }
        authSuccess(token, null)
    }

    private fun authSuccess(accessToken: String, idToken: String?) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().apply {
            putString(GeneralKeys.BRAPI_TOKEN, accessToken)
            putString(GeneralKeys.BRAPI_ID_TOKEN, idToken)
            apply()
        }

        intent?.data = null
        Log.d(TAG, "Auth successful")
        Toast.makeText(this, R.string.brapi_auth_success, Toast.LENGTH_LONG).show()
        setResult(RESULT_OK)
        finish()
    }

    private fun authError(ex: Exception?) {
        intent?.data = null
        Log.e(TAG, "Auth error", ex)
        Toast.makeText(this, R.string.brapi_auth_failed, Toast.LENGTH_LONG).show()
        setResult(RESULT_CANCELED)
        finish()
    }
}
