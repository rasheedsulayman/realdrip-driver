package com.treplabs.android.realdripdriver.auth

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AccessTokenAuthenticator(
    private val tokenProvider: AccessTokenProvider
) : Authenticator {

    companion object {
        const val AUTH_KEY = "req-token"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = tokenProvider.token() ?: return null
        synchronized(this) {
            val newToken = tokenProvider.token()
            if (response.request.header(AUTH_KEY) != null) {
                if (newToken != token) {
                    return response.request
                        .newBuilder()
                        .removeHeader(AUTH_KEY)
                        .addHeader(AUTH_KEY, "$newToken")
                        .build()
                }
                val updatedToken = tokenProvider.refreshToken() ?: return null
                return response.request
                    .newBuilder()
                    .removeHeader(AUTH_KEY)
                    .addHeader(AUTH_KEY, updatedToken)
                    .build()
            }
        }
        return null
    }
}
