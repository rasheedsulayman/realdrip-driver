package com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications

import com.google.gson.annotations.SerializedName

data class NotificationPayload(
    @SerializedName("to")
    var destinationUID: String,
    @SerializedName("data")
    var data: NotificationData
)


data class NotificationData(
    @SerializedName("deviceId")
    var deviceId: String,
    @SerializedName("message")
    var message: String
)

data class NotificationResponse(
    @SerializedName("success")
    var success: Int
)

fun NotificationResponse.isSuccessFull() = success > 0