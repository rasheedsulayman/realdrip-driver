package com.treplabs.android.realdripdriver.networkutils

import com.google.gson.annotations.SerializedName

class BaseAPIResponse<T> {

    @SerializedName("status")
    lateinit var status: String

    @SerializedName("message")
    lateinit var message: String

    @SerializedName("data")
    var data: T? = null

    @SerializedName("errors")
    var errors: Errors? = null
}

data class Errors(
    @SerializedName("fieldName")
    val fieldName: List<String>
)
