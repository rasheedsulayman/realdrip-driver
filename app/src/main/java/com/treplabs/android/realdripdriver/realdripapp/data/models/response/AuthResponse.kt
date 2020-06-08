package com.treplabs.android.realdripdriver.realdripapp.data.models.response
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val nurse: Nurse
)