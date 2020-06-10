package com.treplabs.android.realdripdriver.realdripdriverapp.apis

import com.treplabs.android.realdripdriver.networkutils.BaseAPIResponse
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.request.AuthRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RealDripAPIAuthService {

    @POST("/api/users/login")
    fun getAccessToken(@Body body: AuthRequest): Call<BaseAPIResponse<AuthResponse>>

    @POST("/api/users/login")
    suspend fun login(@Body body: AuthRequest): Response<BaseAPIResponse<AuthResponse>>
}
