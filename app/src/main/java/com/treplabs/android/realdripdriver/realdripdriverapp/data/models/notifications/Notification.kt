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
    val deviceId: String,
    @SerializedName("infusionId")
    val infusionId: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("title")
    val title: String
) {
    companion object {
        fun from(map: MutableMap<String, String>) = NotificationData(
            map["deviceId"]!!,
            map["infusionId"]!!,
            map["message"]!!,
            map["title"]!!
        )
    }
}

data class NotificationToken(@SerializedName("deviceId") val token: String? = null)


data class NotificationResponse(
    @SerializedName("success")
    var success: Int
)

fun NotificationResponse.isSuccessFull() = success > 0