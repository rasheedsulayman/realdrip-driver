package com.treplabs.android.realdripdriver.realdripdriverapp.data.store

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirestoreDataSource @Inject constructor() {

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

    suspend inline fun <reified T> getQuerySnapshot(reference: Query) =
        suspendCancellableCoroutine<List<T>> { continuation ->
            reference.get().addOnSuccessListener {
                if (!continuation.isActive) return@addOnSuccessListener
                continuation.resume(it.toObjects(T::class.java))
            }.addOnFailureListener {
                if (!continuation.isActive) return@addOnFailureListener
                continuation.resumeWithException(it)
            }
        }

    suspend inline fun <reified T> getDocument(reference: DocumentReference) =
        suspendCancellableCoroutine<T> { continuation ->
            reference.get().addOnSuccessListener {
                if (!continuation.isActive) return@addOnSuccessListener
                continuation.resume(it.toObject(T::class.java)!!)
            }.addOnFailureListener {
                if (!continuation.isActive) return@addOnFailureListener
                continuation.resumeWithException(it)
            }
        }


    suspend inline fun <reified T> addDocument(document: T, reference: CollectionReference) =
        suspendCancellableCoroutine<String> { continuation ->
            reference.add(document!!).addOnSuccessListener {
                Timber.d("Document was successfully added to collection. doc reference: ${it.id}")
                continuation.resume(it.id)
            }.addOnFailureListener {
                it.printStackTrace()
                continuation.resumeWithException(it)
            }
        }

    suspend inline fun <reified T> setDocument(reference: DocumentReference, document: T) =
        suspendCancellableCoroutine<String> { continuation ->
            reference.set(document!!).addOnSuccessListener {
                Timber.d("Document was successfully saved with reference ${reference.id}")
                continuation.resume(reference.id)
            }.addOnFailureListener {
                it.printStackTrace()
                continuation.resumeWithException(it)
            }
        }

    suspend fun deleteDocument(reference: CollectionReference, id: String) =
        suspendCancellableCoroutine<Boolean> { continuation ->
            reference.document(id).delete().addOnSuccessListener {
                continuation.resume(true)
            }.addOnFailureListener {
                it.printStackTrace()
                continuation.resumeWithException(it)
            }
        }

}