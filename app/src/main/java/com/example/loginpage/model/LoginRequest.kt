package com.example.loginpage.model

data class LoginRequest(
    val password: String,
    val type: String="",
    val user_id: String,
    val moduleId: String=""
)
