package com.example.loginpage.network


import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import com.example.loginpage.model.SendOtpRequest
import com.example.loginpage.model.SendOtpResponse
import com.example.loginpage.model.VerifyOtpRequest
import com.example.loginpage.model.VerifyOtpResponse

interface OtpApiService {

    @POST("validateOtp")
    suspend fun validateOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("mitraLoginData")
    suspend fun sendOtp(@Body request: SendOtpRequest): Response<SendOtpResponse>
}
