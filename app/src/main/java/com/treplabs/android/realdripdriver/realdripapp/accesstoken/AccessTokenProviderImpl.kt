package com.treplabs.android.realdripdriver.realdripapp.accesstoken

import com.treplabs.android.realdripdriver.auth.AccessTokenProvider
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.realdripapp.data.repositories.OauthRepository
import com.treplabs.android.realdripdriver.utils.PrefsValueHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenProviderImpl @Inject constructor(
    private val oauthRepository: OauthRepository,
    private val prefsValueHelper: PrefsValueHelper
) : AccessTokenProvider {

    override fun token(): String? = prefsValueHelper.getAccessToken()

    override fun refreshToken(): String? {
        return when (val result = oauthRepository.getAccessToken()) {
            is Result.Success -> {
                prefsValueHelper.saveAccessToken(result.data.token)
                result.data.token
            }
            is Result.Error -> null
        }
    }
}
