package com.dicoding.soulsync.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_session"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val USER_TOKEN = "user_token"
    }

    fun saveLoginSession(token: String) {
        val editor = prefs.edit()
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun getUserToken(): String {
        return prefs.getString(USER_TOKEN, "") ?: ""
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
