package com.example.loginpage.model


data class SpotBillingRequest(
    val user_id: String,
    val type: String="b",
    val consumer_type: String="S",
    val search_flag: String="S",
    val version: String="1.0.0"
)
