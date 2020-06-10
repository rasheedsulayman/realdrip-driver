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

class FirebaseRepository @Inject constructor(
    private val firestoreDataSource: FirestoreDataSource
) {

    @ExperimentalCoroutinesApi
    fun observeDeviceInfusion(deviceId: String): Flow<RealtimeInfusion> {
        val deviceReference =
            Firebase.firestore.collection(FireStorePaths.DEVICES).document(deviceId)
        return firestoreDataSource.observeDocumentSnapshot(deviceReference)
    }

    suspend fun setInfusion(deviceId: String, realtimeInfusion: RealtimeInfusion): Result<String> {
        return try {
            val deviceReference = Firebase.firestore.collection(FireStorePaths.DEVICES)
                .document(deviceId)
            Result.Success(firestoreDataSource.setDocument(deviceReference, realtimeInfusion))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE)
        }
    }

    suspend fun getNotificationToken(): Result<String> {
        return try {
            val tokenReference = Firebase.firestore.collection(FireStorePaths.SETTINGS)
                .document(FireStorePaths.NOTIFICATION_TOKEN)
            Result.Success(firestoreDataSource.getDocument(tokenReference))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE)
        }
    }

}