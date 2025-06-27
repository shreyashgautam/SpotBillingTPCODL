package com.example.loginpage.model

data class SendOtpRequest(
    val CA : String="80000427247",
    val EMAIL : String,
    val MOB_NUM:String,
    val REF_CODE:String=""
)