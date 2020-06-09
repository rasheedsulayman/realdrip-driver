package com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val nurse: Nurse
)