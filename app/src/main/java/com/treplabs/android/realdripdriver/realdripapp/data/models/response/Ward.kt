package com.treplabs.android.realdripdriver.realdripapp.data.models.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Ward(
    @SerializedName("_id")
    @PrimaryKey val id: String,
    @SerializedName("hospitalId")
    val hospitalId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("label")
    val label: String,
    @SerializedName("defaultPass")
    val defaultPass: Boolean
) : Parcelable


