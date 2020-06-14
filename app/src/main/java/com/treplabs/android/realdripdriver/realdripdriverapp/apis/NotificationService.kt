package com.treplabs.android.realdripdriver.realdripdriverapp.apis

import com.treplabs.android.realdripdriver.BuildConfig
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications.NotificationPayload
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications.NotificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationService {


    @POST("/fcm/send")
    @Headers("Authorization:key=${BuildConfig.NOTIFICATION_AUTH_TOKEN}")
    suspend fun sendNotification(
        @Body notificationPayload: NotificationPayload): Response<NotificationResponse>
}
