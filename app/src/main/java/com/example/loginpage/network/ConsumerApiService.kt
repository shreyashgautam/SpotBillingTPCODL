package com.example.loginpage.network

import com.example.loginpage.model.ConsumerRequest
import com.example.loginpage.model.ConsumerResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ConsumerApiService {
    @POST("api/Info/getconsumerdetails")
    suspend fun getConsumerBillData(
        @Body request: ConsumerRequest
    ): Response<ConsumerResponse>
}
