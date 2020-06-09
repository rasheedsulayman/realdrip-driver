package com.treplabs.android.realdripdriver.realdripdriverapp.data.models.request

import com.google.gson.annotations.SerializedName

data class AccessTokenRequest(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String
)
