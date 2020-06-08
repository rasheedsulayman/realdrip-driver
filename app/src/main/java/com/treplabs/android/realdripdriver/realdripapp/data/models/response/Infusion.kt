package com.treplabs.android.realdripdriver.realdripapp.data.models.response

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "infusions")
@Parcelize
data class Infusion(
    @SerializedName("doctorsInstruction")
    var doctorsInstruction: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("patientName")
    var patientName: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("volumeToDispense")
    var volumeToDispense: Int,

    // populate-able Ids.
    @SerializedName("nurseId")
    var nurseId: String,
    @SerializedName("deviceId")
    @Embedded(prefix = "deviceId_") var device: Device,
    @SerializedName("wardId")
    @Embedded(prefix = "wardId_") var ward: Ward,
    @SerializedName("hospitalId")
    @Embedded(prefix = "hospitalId_") var hospital: Hospital,

    //TODO Add Missing values from the API. Must be added before we go live
    @SerializedName("diagnosis")
    var diagnosis: String,
    @SerializedName("liquidContent")
    var liquidContent: String,
    @SerializedName("isActive")
    var isActive: Boolean,
    @SerializedName("duration")
    var duration: Long,
    @SerializedName("volumeDispensed")
    var volumeDispensed: Int,
    @SerializedName("timeSpent")
    var timeSpent: Long,

    //TODO This variable is only use to access the local db.
    // remove this when you clean up the local db.
    @PrimaryKey var localRoomId: String = UUID.randomUUID().toString()
) : Parcelable


