package com.treplabs.android.realdripdriver.realdripapp.data.store

import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import timber.log.Timber

@ExperimentalCoroutinesApi
object FlowFirestore {

    inline fun <reified T> observeDocumentSnapshot(reference: DocumentReference) = channelFlow<T> {
        val subscription = reference.addSnapshotListener { snapshot, e ->
            Timber.e(e)
            Timber.d("Received a snapshot $snapshot")
            if (snapshot == null) {
                return@addSnapshotListener
            }
            if (snapshot.exists()) channel.offer(snapshot.toObject(T::class.java)!!)
        }
        // The callback inside awaitClose will be executed when the channel is
        // either closed or cancelled
        awaitClose { subscription.remove() }
    }
}
