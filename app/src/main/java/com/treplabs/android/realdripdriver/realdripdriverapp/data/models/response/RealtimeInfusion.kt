package com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RealtimeInfusion(
    val batteryDuration: String? = null,
    val control: String? = null,
    val flowRate: String? = null,
    val onOperation: Boolean? = null,
    val timeRemaining: String? = null, //Milliseconds
    val volumeGiven: String? = null,  // Ml
    val volumeGivenPercent: String? = null, // Percentage
    val volumeLeft: String? = null,  // ml
    val volumeLeftPercent: String? = null, // ml

    //Todo new data
    val elapsedTime: String? = null, //Milliseconds
    val totalVolume: String? = null, //ml
    val batteryLevelPercentage: String? = null

) : Parcelable {

    override fun toString(): String {
        return "RealtimeInfusion(\nbatteryDuration=$batteryDuration,\ncontrol=$control,\nflowRate=$flowRate,\n" +
                "onOperation=$onOperation,\ntimeRemaining=$timeRemaining,\nvolumeGiven=$volumeGiven,\n" +
                "volumeGivenPercent=$volumeGivenPercent,\nvolumeLeft=$volumeLeft,\nvolumeLeftPercent=$volumeLeftPercent,\n" +
                "elapsedTime=$elapsedTime,\ntotalVolume=$totalVolume,\nbatteryLevelPercentage=$batteryLevelPercentage\n)"
    }
}

