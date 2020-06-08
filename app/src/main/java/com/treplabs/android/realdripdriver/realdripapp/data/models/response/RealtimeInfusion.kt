package com.treplabs.android.realdripdriver.realdripapp.data.models.response
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RealtimeInfusion(
    val batteryDuration: String? = null,
    val control: String? = null,
    val flowRate: String? = null,
    val onOperation: String? = null,
    val timeRemaining: String? = null, //Milliseconds
    val volumeGiven: String? = null,  // Ml
    val volumeGivenPercent: String? = null, // Percentage
    val volumeLeft: String? = null,  // ml
    val volumeLeftPercent: String? = null, // ml

    //Todo new data
    val elapsedTime: String? = null, //Milliseconds
    val totalVolume: String? = null, //ml
    val batteryLevelPercentage: String? = null

) : Parcelable

