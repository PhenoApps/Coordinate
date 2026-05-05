package org.wheatgenetics.coordinate.brapi.dialogs

import android.content.Context
import org.json.JSONObject
import org.phenoapps.brapi.ui.BrapiAccountConfig
import org.phenoapps.brapi.ui.BrapiAccountUiState
import org.wheatgenetics.coordinate.R

internal fun defaultBrapiAccountState(context: Context): BrapiAccountUiState {
    val implicitFlow = context.getString(org.phenoapps.brapi.R.string.pheno_brapi_oidc_flow_oauth_implicit)
    return BrapiAccountUiState(
        oidcFlow = implicitFlow,
        brapiVersion = "V2",
        oidcClientId = context.getString(R.string.brapi_oidc_clientid_default),
    )
}

internal fun BrapiAccountUiState.withUrlUpdate(url: String): BrapiAccountUiState {
    val derivedOidcUrl = if (!oidcUrlExplicitlySet && url.isNotEmpty() && url != "https://") {
        url.trimEnd('/') + "/.well-known/openid-configuration"
    } else {
        oidcUrl
    }
    return copy(url = url, oidcUrl = derivedOidcUrl)
}

internal fun parseBrapiConfig(json: String): BrapiAccountConfig? = runCatching {
    val obj = JSONObject(json)
    BrapiAccountConfig(
        url = obj.optString("url").takeIf { it.isNotEmpty() },
        name = obj.optString("name").takeIf { it.isNotEmpty() },
        version = obj.optString("version").takeIf { it.isNotEmpty() },
        authFlow = obj.optString("authFlow").takeIf { it.isNotEmpty() },
        oidcUrl = obj.optString("oidcUrl").takeIf { it.isNotEmpty() },
        clientId = obj.optString("clientId").takeIf { it.isNotEmpty() },
        scope = obj.optString("scope").takeIf { it.isNotEmpty() },
        pageSize = obj.optString("pageSize").takeIf { it.isNotEmpty() },
        chunkSize = obj.optString("chunkSize").takeIf { it.isNotEmpty() },
        serverTimeoutMilli = obj.optString("serverTimeoutMilli").takeIf { it.isNotEmpty() },
    )
}.getOrNull()

internal fun BrapiAccountUiState.withConfig(config: BrapiAccountConfig): BrapiAccountUiState =
    copy(
        url = config.url ?: url,
        displayName = config.name ?: displayName,
        brapiVersion = when {
            config.version.equals("v1", ignoreCase = true) -> "V1"
            config.version.equals("v2", ignoreCase = true) -> "V2"
            else -> brapiVersion
        },
        oidcFlow = config.authFlow ?: oidcFlow,
        oidcUrl = config.oidcUrl ?: oidcUrl,
        oidcClientId = config.clientId ?: oidcClientId,
        oidcScope = config.scope ?: oidcScope,
        oidcUrlExplicitlySet = !config.oidcUrl.isNullOrEmpty() || oidcUrlExplicitlySet,
    )

internal fun isValidBrapiUrl(url: String): Boolean {
    if (url.contains(' ')) return false
    return runCatching {
        val parsed = java.net.URL(url)
        val scheme = parsed.protocol
        val host = parsed.host ?: return false
        (scheme == "http" || scheme == "https") && host.isNotEmpty() &&
            (host.contains('.') || host.startsWith('[') || host == "localhost")
    }.getOrDefault(false)
}
