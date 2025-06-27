package com.example.loginpage.network

import com.example.loginpage.model.SpotBillingRequest
import com.example.loginpage.model.SpotBillingResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SpotBillingApiService {


    @Headers("Content-Type: application/json")
    @POST("sbm/download-spot-billing-data")
    suspend fun getSpotBillingData(
        @Header("authorization") token : String,
        @Body request: JsonObject
    ): Response<SpotBillingResponse>
}
