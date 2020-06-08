package com.treplabs.android.realdripdriver.realdripapp.data.repositories

import com.treplabs.android.realdripdriver.networkutils.GENERIC_ERROR_CODE
import com.treplabs.android.realdripdriver.networkutils.GENERIC_ERROR_MESSAGE
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.networkutils.getAPIResult
import com.treplabs.android.realdripdriver.realdripapp.apis.RealDripAPIAuthService
import com.treplabs.android.realdripdriver.realdripapp.data.models.request.AuthRequest
import com.treplabs.android.realdripdriver.realdripapp.data.models.response.AuthResponse
import com.treplabs.android.realdripdriver.utils.PrefsValueHelper
import timber.log.Timber
import javax.inject.Inject

class OauthRepository @Inject constructor(
    private val oauthService: RealDripAPIAuthService,
    private val prefsValueHelper: PrefsValueHelper
) {

    fun getAccessToken(): Result<AuthResponse> {
        return try {
            getAPIResult(oauthService.getAccessToken(prefsValueHelper.getAuthRequest()).execute())
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE)
        }
    }

    suspend fun login(authRequest: AuthRequest): Result<AuthResponse> {
        return try {
            getAPIResult(oauthService.login(authRequest))
        } catch (e: Exception) {
            Timber.e(e)
            Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE)
        }
    }
}
