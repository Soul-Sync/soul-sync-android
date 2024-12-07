package com.dicoding.soulsync.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreference(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("user_token")
    private val NAME_KEY = stringPreferencesKey("user_name")

    // Save Name
    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    // Get Name
    val name: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[NAME_KEY]
        }


    // Save Token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Get Token
    val token: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }

    // Clear Token
    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
