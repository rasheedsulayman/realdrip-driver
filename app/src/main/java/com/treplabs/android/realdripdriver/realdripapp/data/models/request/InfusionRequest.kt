package com.treplabs.android.realdripdriver.realdripapp.data.models.request

import com.google.gson.annotations.SerializedName

data class CreateInfusionRequest(
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("doctorsInstruction")
    val doctorsInstruction: String,
    @SerializedName("patientName")
    val patientName: String,
    @SerializedName("volumeToDispense")
    val volumeToDispense: Int,

    //TODO new data
    @SerializedName("diagnosis")
    val diagnosis: String,
    @SerializedName("liquidContent")
    val liquidContent: String
)

data class UpdateInfusionRequest(

    @SerializedName("doctorsInstruction")
    val doctorsInstruction: String? = null,
    @SerializedName("patientName")
    val patientName: String? = null,

    //TODO new data
    @SerializedName("diagnosis")
    val diagnosis: String? = null,
    @SerializedName("liquidContent")
    val liquidContent: String? = null,

    @SerializedName("status")
    val status: String? = null,
    @SerializedName("timeSpent")
    val timeSpent: Long? = null,
    @SerializedName("actualVolumeDispensed")
    val actualVolumeDispensed: Int? = null
)
