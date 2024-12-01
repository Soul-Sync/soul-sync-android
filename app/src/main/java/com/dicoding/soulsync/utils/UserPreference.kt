package com.dicoding.soulsync.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.soulsync.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Ekstensi DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreference private constructor(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("user_token")
        private val STATUS_KEY = stringPreferencesKey("login_status")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(context: Context): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(context)
                INSTANCE = instance
                instance
            }
        }
    }

    // Menyimpan sesi login
    suspend fun saveSession(loginResponse: LoginResponse) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = loginResponse.token ?: ""
            preferences[STATUS_KEY] = loginResponse.status
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    // Mendapatkan sesi login
    fun getSession(): Flow<LoginResponse> {
        return context.dataStore.data.map { preferences ->
            LoginResponse(
                status = preferences[STATUS_KEY] ?: "unknown",
                message = "Session data retrieved",
                token = preferences[TOKEN_KEY]
            )
        }
    }

    // Mengecek apakah pengguna sudah login
    fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }
    }

    // Logout (hapus sesi)
    suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
