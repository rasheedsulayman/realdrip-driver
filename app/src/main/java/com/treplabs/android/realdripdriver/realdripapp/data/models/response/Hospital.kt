package com.treplabs.android.realdripdriver.realdripapp.data.models.response
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Hospital(
    @SerializedName("confirmedEmail")
    val confirmedEmail: Boolean,
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    @PrimaryKey val id: String,
    @SerializedName("location")
    @Embedded val location: Location,
    @SerializedName("name")
    val name: String,
    @SerializedName("verifiedAccount")
    val verifiedAccount: Boolean
) : Parcelable

@Entity
@Parcelize
data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("state")
    val state: String
) : Parcelable
