package com.treplabs.android.realdripdriver.realdripdriverapp.screens.infusiondetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.networkutils.LoadingStatus
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.networkutils.Event
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response.RealtimeInfusion
import com.treplabs.android.realdripdriver.realdripdriverapp.data.repositories.FirestoreRepository
import com.treplabs.android.realdripdriver.utils.PrefsValueHelper
import com.treplabs.android.realdripdriver.utils.ResourceProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("EXPERIMENTAL_API_USAGE")
class InfusionDetailsViewModel @Inject constructor(
    private val prefsValueHelper: PrefsValueHelper,
    private val firestoreRepository: FirestoreRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _realtimeInfusion = MutableLiveData<RealtimeInfusion>()
    val realtimeInfusion: LiveData<RealtimeInfusion>
        get() = _realtimeInfusion

    private val _sendNotification = MutableLiveData<Event<Boolean>>()
    val sendNotification: LiveData<Event<Boolean>>
        get() = _sendNotification


    fun createInfusion(request: CreateInfusionRequest) {
        _loadingStatus.value = LoadingStatus.Loading("Creating Infusion, Please wait")
        viewModelScope.launch {
            when (val result = infusionRepository.createInfusion(request)) {
                is Result.Success -> {
                    _loadingStatus.value = LoadingStatus.Success
                    _navigateToTreatment.value =
                        Event(true)
                }
                is Result.Error -> _loadingStatus.value =
                    LoadingStatus.Error(result.errorCode, result.errorMessage)
            }
        }
    }


    fun sendNotification() {

    }

    fun resetInfusion(volumeToDispense: Int) {

    }

    fun startSimulation(volumeToDispense: Int) {

        viewModelScope.launch {

        }

    }



    private suspend fun subscribeToRealTimeInfusion(infusionId: String) {
        firestoreRepository.observeDeviceInfusion(infusionId).collect {
            _realtimeInfusion.value = it
        }
    }
}
