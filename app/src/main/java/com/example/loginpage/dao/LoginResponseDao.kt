package com.example.loginpage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.loginpage.model.LoginResponseEntity

@Dao
interface LoginResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginResponse(response: LoginResponseEntity)

    @Query("SELECT * FROM login_response WHERE user_id = :userId")
    suspend fun getLoginResponse(userId: String): LoginResponseEntity?





    @Query("SELECT * FROM login_response")
    suspend fun getAllLoginResponses(): List<LoginResponseEntity>

    // Naya function jo pehla user return karega
    @Query("SELECT * FROM login_response LIMIT 1")
    suspend fun getAnyLoginResponse(): LoginResponseEntity?


    @Query("DELETE FROM login_response")
    suspend fun deleteAll()
}
