package com.treplabs.android.realdripdriver.realdripdriverapp.screens.infusiondetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.treplabs.android.realdripdriver.base.BaseViewModel
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.networkutils.Event
import com.treplabs.android.realdripdriver.realdripdriverapp.apis.NotificationService
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications.NotificationData
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications.NotificationPayload
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications.isSuccessFull
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response.RealtimeInfusion
import com.treplabs.android.realdripdriver.realdripdriverapp.data.repositories.FirebaseRepository
import com.treplabs.android.realdripdriver.realdripdriverapp.data.repositories.NotificationRepository
import com.treplabs.android.realdripdriver.realdripdriverapp.screens.devicedetails.InfusionDetails
import com.treplabs.android.realdripdriver.utils.PrefsValueHelper
import com.treplabs.android.realdripdriver.utils.ResourceProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@Suppress("EXPERIMENTAL_API_USAGE")
class InfusionDetailsViewModel @Inject constructor(
    private val prefsValueHelper: PrefsValueHelper,
    private val firebaseRepository: FirebaseRepository,
    private val notificationRepository: NotificationRepository,
    private val resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _realtimeInfusion = MutableLiveData<RealtimeInfusion>()
    val realtimeInfusion: LiveData<RealtimeInfusion>
        get() = _realtimeInfusion

    private val _sendNotification = MutableLiveData<Event<Boolean>>()
    val sendNotification: LiveData<Event<Boolean>>
        get() = _sendNotification


    private lateinit var simulationScope: CoroutineScope

    // TODO find a way to cancel simulation.
    fun restartSimulation(infusionDetails: InfusionDetails) {
        stopSimulation()
        simulationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        simulationScope.launch {
            val infusion = infusionDetails.realtimeInfusion
            val milPerSec = infusion.flowRate!!.toFloat() / (60 * 60)
            var volumeDispensed = 0f
            val volumeToDispense = infusion.totalVolume!!.toInt()
            val totalTimeToSpend =
                ((volumeToDispense / infusion.flowRate.toFloat()) * 60 * 60 * 1000).toLong()
            var timeSpent = 0L
            var batteryPercentage = 100
            while (volumeDispensed < volumeToDispense) {
                val ONE_MILISS = 1000L
                delay(ONE_MILISS)
                volumeDispensed += milPerSec
                val volumeLeft = (volumeToDispense - volumeDispensed)
                timeSpent += ONE_MILISS
                val timeLeft = (totalTimeToSpend - timeSpent).coerceAtLeast(0)
                val volumeGivenPercentage = ((volumeDispensed / volumeToDispense) * 100).toInt()
                val volumeLeftPercentage = (100 - volumeGivenPercentage)
                batteryPercentage = volumeLeftPercentage

                val updatedInfusion = RealtimeInfusion(
                    "100",
                    "start",
                    infusion.flowRate,
                    true,
                    timeLeft.toString(),
                    volumeDispensed.toInt().toString(),
                    volumeGivenPercentage.toString(),
                    volumeLeft.toInt().toString(),
                    volumeLeftPercentage.toString(),
                    timeSpent.toString(),
                    volumeToDispense.toString(),
                    batteryPercentage.toString()
                )
                when (val result =
                    firebaseRepository.setInfusion(infusionDetails.deviceId, updatedInfusion)) {
                    is Result.Success -> {
                        Timber.d("Infusion successfully updated")
                    }
                    is Result.Error -> {
                        Timber.d("Error ☹️: ${result.errorMessage}")
                    }
                }
            }
        }
    }


    private suspend fun sendNotification(token: String, dripDeviceId: String, message: String) {
        val notificationPayload =
            NotificationPayload(token, NotificationData(dripDeviceId, message))
        when (val result = notificationRepository.sendNotification(notificationPayload)) {
            is Result.Success -> {
                _sendNotification.value = Event(result.data.isSuccessFull())
            }
            is Result.Error -> {
                Timber.d("Error ☹️: ${result.errorMessage}")
            }
        }
    }

    fun sendNotification(deviceId: String) {
        val currentInfusion = _realtimeInfusion.value!!
        val message = """
            Hello, The content of the infusion with label $deviceId is ${currentInfusion.volumeGivenPercent}% 
            done. Click the notification to see more. 
        """.trimIndent()
        viewModelScope.launch {
            when (val result = firebaseRepository.getNotificationToken()) {
                is Result.Success -> {
                    sendNotification(result.data, deviceId,  message)
                }
                is Result.Error -> {
                    Timber.d("Error ☹️: ${result.errorMessage}")
                }
            }
        }
    }

    fun resetInfusion(infusionDetails: InfusionDetails) {
        stopSimulation()
        viewModelScope.launch {
            _realtimeInfusion.value = infusionDetails.realtimeInfusion
            firebaseRepository.setInfusion(
                infusionDetails.deviceId,
                infusionDetails.realtimeInfusion
            )
        }
    }

    fun stopSimulation() {
        if ((::simulationScope.isInitialized) && simulationScope.isActive) simulationScope.cancel()
    }

    private suspend fun subscribeToRealTimeInfusion(infusionDetails: InfusionDetails) {
        viewModelScope.launch {
            _realtimeInfusion.value = infusionDetails.realtimeInfusion //As start
            firebaseRepository.observeDeviceInfusion(infusionDetails.deviceId).collect {
                _realtimeInfusion.value = it
            }
        }
    }
}
