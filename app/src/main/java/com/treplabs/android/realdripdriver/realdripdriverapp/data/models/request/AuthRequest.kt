package com.treplabs.android.realdripdriver.realdripdriverapp.data.models.request
import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("userType")
    val userType: String
)
