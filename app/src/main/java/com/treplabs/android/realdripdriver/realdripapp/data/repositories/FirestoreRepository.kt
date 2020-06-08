package com.treplabs.android.realdripdriver.realdripapp.data.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.android.realdripdriver.Constants.FireStorePaths
import com.treplabs.android.realdripdriver.realdripapp.data.models.response.RealtimeInfusion
import com.treplabs.android.realdripdriver.realdripapp.data.store.FlowFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreRepository @Inject constructor() {

    @ExperimentalCoroutinesApi
    fun observeDeviceInfusion(deviceId: String) : Flow<RealtimeInfusion> {
        val deviceReference = Firebase.firestore.collection(FireStorePaths.DEVICES).document(deviceId)
        return FlowFirestore.observeDocumentSnapshot(deviceReference)
    }

}