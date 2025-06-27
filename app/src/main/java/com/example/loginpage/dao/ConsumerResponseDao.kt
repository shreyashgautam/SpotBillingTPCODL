package com.example.loginpage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.loginpage.model.ConsumerResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumerResponseDao {

    @Upsert
    suspend fun insertAll(responses: List<ConsumerResponseEntity>)

    @Query("SELECT * FROM consumer_response WHERE KNO = :consumerNumber")
    suspend fun getConsumerData(consumerNumber: String): ConsumerResponseEntity?

    @Query("SELECT * FROM consumer_response")
    fun getAllResponses(): Flow<List<ConsumerResponseEntity>>

    // 🔴 New method to delete all records
    @Query("DELETE FROM consumer_response")
    suspend fun deleteAllResponses()
}
