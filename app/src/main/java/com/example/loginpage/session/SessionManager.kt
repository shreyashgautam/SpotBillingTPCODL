package com.example.loginpage.session

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "user_session")

class SessionManager(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val TIMESTAMP_KEY = longPreferencesKey("login_timestamp")
        private const val TEN_DAYS_MILLIS = 10 * 24 * 60 * 60 * 1000L
    }

    suspend fun saveSession(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[TIMESTAMP_KEY] = System.currentTimeMillis()
        }
    }

    suspend fun isSessionValid(): Boolean {
        val prefs = context.dataStore.data.first()
        val timestamp = prefs[TIMESTAMP_KEY] ?: return false
        val currentTime = System.currentTimeMillis()
        return currentTime - timestamp <= TEN_DAYS_MILLIS
    }

    suspend fun getToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[TOKEN_KEY]
    }

    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
