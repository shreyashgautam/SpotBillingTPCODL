package com.example.loginpage.model

data class VerifyOtpResponse(
    val response: VResponseDetail
)

data class VResponseDetail(
    val status: Boolean,
    val message: String,
    val responseCode: String
)
