package com.treplabs.android.realdripdriver.realdripapp.data.models.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Device(
    @SerializedName("hospitalId")
    val hospitalId: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("wardId")
    val wardId: String
) : Parcelable
