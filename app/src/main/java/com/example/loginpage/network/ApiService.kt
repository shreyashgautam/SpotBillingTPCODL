package com.example.loginpage.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import com.example.loginpage.model.LoginRequest
import com.example.loginpage.model.LoginResponse
import com.example.loginpage.model.SpotBillingResponse
import com.google.gson.JsonObject
import retrofit2.http.Header
import retrofit2.http.Headers


interface ApiService {

    @POST("users/login") // Change this if your endpoint is different
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("sbm/download-spot-billing-data")
    suspend fun getSpotBillingData(
        @Header("authorization") token : String,
        @Body request: JsonObject
    ): Response<SpotBillingResponse>


}
