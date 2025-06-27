package com.example.loginpage.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object OtpRetrofitClient {

    private const val BASE_URL_OTP = "https://portal.tpcentralodisha.com:4114/generate_ref_code/"
//    private const val BASE_URL_OTP = "https://portal.tpcentralodisha.com:4114/generate_ref_code/"
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val authClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthCheck()) // Make sure AuthCheck() is implemented properly
            .build()
    }

    val api: OtpApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_OTP)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OtpApiService::class.java)
    }
}

