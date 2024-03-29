package com.treplabs.android.realdripdriver.utils

import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.request.AuthRequest
import javax.inject.Inject

class PrefsValueHelper @Inject constructor(private val prefsUtils: PrefsUtils) {

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val NURSE = "NURSE "
        const val USER_PROFILE_SUMMARY = "USER_PROFILE_SUMMARY"
        const val LOGGED_IN_USER = "LOGGED_IN_USER"
        const val AUTH_REQUEST = "AUTH_REQUEST"
    }

    fun getAccessToken() = prefsUtils.getString(ACCESS_TOKEN, null)

    fun saveAccessToken(accessToken: String) = prefsUtils.putString(ACCESS_TOKEN, accessToken)

    fun saveAuthRequest(authRequest: AuthRequest) = prefsUtils.putObject(AUTH_REQUEST, authRequest)
    fun getAuthRequest(): AuthRequest = prefsUtils.getPrefAsObject(AUTH_REQUEST)

}
