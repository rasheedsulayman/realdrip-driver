package com.treplabs.android.realdripdriver.realdripdriverapp.data.store

data class DataAndUnit(val data: String, val unit: String)

object FirebaseDataParser {

    //Return either minute or seconds.
    fun getDisplayTime(timeMillis: Long) = if (timeMillis <= 60_000) {
        DataAndUnit("${timeMillis / 60}", "sec")
    } else DataAndUnit("${timeMillis / (60 * 60)}", "min")

    fun getDisplayVolumePercentage(percentage: Int) = DataAndUnit("$percentage", "%")

    fun getDisplayFlowRate(flowRate: Int) = DataAndUnit("$flowRate", "ml/hr")

    fun getDisplayVolume(volume: Int) = DataAndUnit("$volume", "ml") //ml

}