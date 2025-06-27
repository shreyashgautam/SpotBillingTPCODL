package com.example.loginpage.model

data class SendOtpResponse(
    val response: ResponseDetail?=null
)

data class ResponseDetail(
    val status: Boolean,
    val message: String,
    val responseCode: String
)
