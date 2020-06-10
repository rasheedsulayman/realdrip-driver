package com.treplabs.android.realdripdriver.realdripdriverapp.screens.devicedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.networkutils.Event
import com.treplabs.android.realdripdriver.networkutils.LoadingStatus
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.realdripdriverapp.data.repositories.FirebaseRepository
import com.treplabs.android.realdripdriver.utils.PrefsValueHelper
import com.treplabs.android.realdripdriver.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeviceDetailsViewModel @Inject constructor(
    private val prefsValueHelper: PrefsValueHelper,
    private val firebaseRepository: FirebaseRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _navigateInfusionDetails = MutableLiveData<Event<InfusionDetails>>()

    val navigateInfusionDetails: LiveData<Event<InfusionDetails>>
        get() = _navigateInfusionDetails

    fun setInfusion(deviceId: String, volumeToDispense: String) {
        _loadingStatus.value = LoadingStatus.Loading("Initializing Infusion, Please wait...")
        viewModelScope.launch {
            val realtimeInfusion = InfusionDataHelper.getStockInfusion(volumeToDispense)
            when (val result = firebaseRepository.setInfusion(deviceId, realtimeInfusion)) {
                is Result.Success -> {
                    _navigateInfusionDetails.value = Event(InfusionDetails(deviceId, realtimeInfusion))
                    _loadingStatus.value = LoadingStatus.Success
                }
                is Result.Error -> _loadingStatus.value =
                    LoadingStatus.Error(result.errorCode, result.errorMessage)
            }
        }
    }
}
