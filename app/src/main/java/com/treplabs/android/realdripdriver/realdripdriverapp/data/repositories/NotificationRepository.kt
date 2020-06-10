package com.treplabs.android.realdripdriver.realdripdriverapp.data.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.android.realdripdriver.Constants.FireStorePaths
import com.treplabs.android.realdripdriver.networkutils.GENERIC_ERROR_CODE
import com.treplabs.android.realdripdriver.networkutils.GENERIC_ERROR_MESSAGE
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response.RealtimeInfusion
import com.treplabs.android.realdripdriver.realdripdriverapp.data.store.FirestoreDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.treplabs.android.realdripdriver.networkutils.Result
import com.treplabs.android.realdripdriver.realdripdriverapp.apis.NotificationService
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications.NotificationPayload
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.notifications.NotificationResponse

class NotificationRepository @Inject constructor(
    private val notificationService: NotificationService
) {

    suspend fun sendNotification(notificationPayload: NotificationPayload): Result<NotificationResponse> {
        return try {
            val response = notificationService.sendNotification(notificationPayload)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE)
        }
    }

}