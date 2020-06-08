package com.treplabs.android.realdripdriver.realdripapp.screens.createtreatment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.networkutils.LoadingStatus
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.realdripapp.data.models.request.CreateInfusionRequest
import com.treplabs.android.realdripdriver.realdripapp.data.models.request.UpdateInfusionRequest
import com.treplabs.android.realdripdriver.realdripapp.data.models.response.Infusion
import com.treplabs.android.realdripdriver.realdripapp.data.repositories.InfusionRepository
import com.treplabs.android.realdripdriver.networkutils.Event
import com.treplabs.android.realdripdriver.utils.PrefsValueHelper
import com.treplabs.android.realdripdriver.utils.ResourceProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class ManageTreatmentViewModel @Inject constructor(
    private val prefsValueHelper: PrefsValueHelper,
    private val infusionRepository: InfusionRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _navigateToTreatment = MutableLiveData<Event<Boolean>>()

    val navigateToTreatment: LiveData<Event<Boolean>>
        get() = _navigateToTreatment

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

    fun updateInfusion(infusion: Infusion, request: CreateInfusionRequest) {
        val infusionRequest = UpdateInfusionRequest(
            request.doctorsInstruction,
            request.patientName,
            request.diagnosis,
            request.liquidContent
        )
        _loadingStatus.value = LoadingStatus.Loading("Updating Infusion details, Please wait")
        viewModelScope.launch {
            when (val result = infusionRepository.updateInfusion(infusion.id, infusionRequest)) {
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
}
