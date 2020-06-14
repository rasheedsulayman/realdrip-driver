package com.treplabs.android.realdripdriver.realdripdriverapp.screens.infusiondetails

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

    val infusionDump = Transformations.map(_realtimeInfusion){ it.toString() }

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
            var batteryPercentage: Int
            while (volumeDispensed < volumeToDispense) {
                val ONE_SECOND = 1000L
                delay(ONE_SECOND)
                volumeDispensed += (milPerSec)
                val volumeLeft = (volumeToDispense - volumeDispensed.toInt())
                timeSpent += ONE_SECOND
                val timeLeft = (totalTimeToSpend - timeSpent).coerceAtLeast(0)
                val volumeGivenPercentage = ((volumeDispensed / volumeToDispense) * 100).toInt()
                val volumeLeftPercentage = (100 - volumeGivenPercentage)
                batteryPercentage = 100 - (volumeGivenPercentage/2)
                val updatedInfusion = RealtimeInfusion(
                    "100",
                    "start",
                    infusion.flowRate,
                    true,
                    timeLeft.toString(),
                    volumeDispensed.toInt().toString(),
                    volumeGivenPercentage.toString(),
                    volumeLeft.toString(),
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

    private suspend fun sendNotification(notificationPayload: NotificationPayload) {
        when (val result = notificationRepository.sendNotification(notificationPayload)) {
            is Result.Success -> {
                _sendNotification.value = Event(result.data.isSuccessFull())
            }
            is Result.Error -> {
                Timber.d("Error ☹️: ${result.errorMessage}")
            }
        }
    }

    fun sendNotification(infusionId: String, deviceId: String) {
        val currentInfusion = _realtimeInfusion.value!!
        val title = "Infusion Alert"
        val message = """
            Hello, The content of the infusion with label $deviceId is ${currentInfusion.volumeGivenPercent}% 
            done. The infusion in ${DateUtils.formatElapsedTime(currentInfusion.timeRemaining!!.toLong() / 1000)}.
            Click the notification to see more. 
        """.trimIndent()
        NotificationData(deviceId, infusionId, message, title)
        viewModelScope.launch {
            when (val result = firebaseRepository.getNotificationToken()) {
                is Result.Success -> {
                    sendNotification(
                        NotificationPayload(
                            result.data.token!!,
                            NotificationData(deviceId, infusionId, message, title)
                        )
                    )
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
        if ((::simulationScope.isInitialized) && simulationScope.isActive){
            Timber.d("About to cancel simulation")
            simulationScope.cancel()
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopSimulation()
    }

    fun subscribeToRealTimeInfusion(infusionDetails: InfusionDetails) {
        viewModelScope.launch {
            _realtimeInfusion.value = infusionDetails.realtimeInfusion //As start
            firebaseRepository.observeDeviceInfusion(infusionDetails.deviceId).collect {
                _realtimeInfusion.value = it
            }
        }
    }
}
