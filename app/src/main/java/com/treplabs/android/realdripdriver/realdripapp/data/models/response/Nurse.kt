package com.treplabs.android.realdripdriver.realdripapp.data.models.response
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Nurse(
    @SerializedName("confirmedEmail")
    val confirmedEmail: Boolean,
    @SerializedName("defaultPass")
    val defaultPass: Boolean,
    @SerializedName("email")
    val email: String,
    @SerializedName("hospitalId")
    val hospitalId: String,
    @SerializedName("_id")
    @PrimaryKey val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNo")
    val phoneNo: String,
    @SerializedName("verifiedAccount")
    val verifiedAccount: Boolean,
    @SerializedName("wardId")
    val wardId: String
)
