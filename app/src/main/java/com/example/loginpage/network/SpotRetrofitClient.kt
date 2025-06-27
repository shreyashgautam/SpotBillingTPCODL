package com.example.loginpage.api

import com.example.loginpage.network.SpotBillingApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.immutableListOf
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Interceptor to add Bearer token to each request
class AuthInterceptor(private val tokenProvider: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider()

        // Add Authorization header only if token is not null or empty
        val newRequest = if (!token.isNullOrEmpty()) {
            originalRequest.newBuilder()
                //.addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        return chain.proceed(newRequest)
    }
}

// RetrofitInstance singleton with token injection support
object RetrofitInstance {

    private const val BASE_URL = "https://billingapis.tpcentralodisha.com/tatapower-ws-prod/"

    // Provide tokenProvider lambda to get current token
    fun getRetrofitInstance(tokenProvider: () -> String?): SpotBillingApiService {
        val authInterceptor = AuthInterceptor(tokenProvider)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set the desired log level
        }
        val client = OkHttpClient.Builder()

            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }

            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .protocols(immutableListOf(Protocol.HTTP_1_1))
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotBillingApiService::class.java)
    }
}
