package com.treplabs.android.realdripdriver.realdripdriverapp.apis

import com.treplabs.android.realdripdriver.networkutils.BaseAPIResponse
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.request.CreateInfusionRequest
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.request.UpdateInfusionRequest
import com.treplabs.android.realdripdriver.realdripdriverapp.data.models.response.Infusion
import retrofit2.Response
import retrofit2.http.*

interface RealDripApiService {

    // TODO add API endpoints

    @GET("/api/infusion")
    suspend fun getInfusions(
        @Query("status") status: String,
        @Query("populate") populate: String,
        @Query("wardId") wardId: String,
        @Query("nurseId") nurseId: String,
        @Query("deviceId") deviceId: String?
    ): Response<BaseAPIResponse<List<Infusion>>>

    @GET("/api/infusion/{infusionId}")
    suspend fun getInfusion(
        @Path("infusionId") infusionId: String,
        @Query("populate") populate: String
    ): Response<BaseAPIResponse<Infusion>>

    @POST("/api/infusion")
    suspend fun createInfusion(
        @Body payload: CreateInfusionRequest
    ): Response<BaseAPIResponse<Infusion>>

    @PUT("/api/infusion/{infusionId}")
    suspend fun updateInfusion(
        @Path("infusionId") infusionId: String,
        @Body payload: UpdateInfusionRequest
    ): Response<BaseAPIResponse<Infusion>>

}
