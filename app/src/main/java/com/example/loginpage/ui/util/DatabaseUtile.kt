package com.example.loginpage.ui.util


import android.content.Context
import com.example.loginpage.helpers.DatabaseBuilder

suspend fun getUserIdFromDb(context: Context): String? {
    val loginResponseDao = DatabaseBuilder.getInstance(context).loginResponseDao()
    val loginResponse = loginResponseDao.getAnyLoginResponse()
    return loginResponse?.user_id
}

// suspend function to get token from DB (similar to getUserIdFromDb)
suspend fun getTokenFromDb(context: Context): String? {
    val loginResponseDao = DatabaseBuilder.getInstance(context).loginResponseDao()
    val loginResponse = loginResponseDao.getAnyLoginResponse()
    return loginResponse?.token // assuming token is stored in loginResponse entity
}
