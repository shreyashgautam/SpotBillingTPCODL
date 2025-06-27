package com.example.loginpage.helpers


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.loginpage.dao.ConsumerResponseDao
import com.example.loginpage.dao.LoginResponseDao
import com.example.loginpage.model.ConsumerResponseEntity
import com.example.loginpage.model.LoginResponseEntity

@Database(entities = [LoginResponseEntity::class, ConsumerResponseEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun loginResponseDao(): LoginResponseDao
    abstract fun conResponseDao(): ConsumerResponseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Singleton instance
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // optionally add migration or fallbackToDestructiveMigration
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


