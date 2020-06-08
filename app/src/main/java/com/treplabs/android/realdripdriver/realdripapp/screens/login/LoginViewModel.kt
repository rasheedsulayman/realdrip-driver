package com.treplabs.android.realdripdriver.realdripapp.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treplabs.android.realdripdriver.Constants
import com.treplabs.android.realdripdriver.R
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.networkutils.Event
import com.treplabs.android.realdripdriver.networkutils.LoadingStatus
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.realdripapp.data.models.request.AuthRequest
import com.treplabs.android.realdripdriver.realdripapp.data.repositories.OauthRepository
import com.treplabs.android.realdripdriver.utils.PrefsValueHelper
import com.treplabs.android.realdripdriver.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val prefsValueHelper: PrefsValueHelper,
    private val oauthRepository: OauthRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _navigateDashBoard = MutableLiveData(Event(false))

    val navigateDashBoard: LiveData<Event<Boolean>>
        get() = _navigateDashBoard

    fun logIn(email: String, password: String) {
        val authRequest = AuthRequest(email, password, Constants.APIDataKeys.NURSE)
        _loadingStatus.value =
            LoadingStatus.Loading(resourceProvider.getString(R.string.logging_in))
        viewModelScope.launch {
            when (val result = oauthRepository.login(authRequest)) {
                is Result.Success -> {
                    prefsValueHelper.saveAuthRequest(authRequest)
                    prefsValueHelper.saveLoggedInUser(result.data.nurse)
                    _loadingStatus.value = LoadingStatus.Success
                    _navigateDashBoard.value = Event(true)
                }
                is Result.Error -> _loadingStatus.value =
                    LoadingStatus.Error(result.errorCode, result.errorMessage)
            }
        }
    }

}
