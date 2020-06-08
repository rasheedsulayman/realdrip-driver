package com.treplabs.android.realdripdriver.auth

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val tokenProvider: AccessTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.token()
        return if (token == null) {
            chain.proceed(chain.request())
        } else {
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader(AccessTokenAuthenticator.AUTH_KEY, token)
                .build()
            chain.proceed(authenticatedRequest)
        }
    }
}
