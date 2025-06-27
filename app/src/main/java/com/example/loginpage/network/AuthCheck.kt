package com.example.loginpage.network
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthCheck: Interceptor {
    private val username = "refCode_user"
    private val password = "referralpassword@123"

    override fun intercept(chain: Interceptor.Chain): Response {
        val credentials = Credentials.basic(username, password)
        val request = chain.request().newBuilder()
            .addHeader("Authorization", credentials)
            .build()
        return chain.proceed(request)
    }

}