package com.capstone.sampahin.data

import android.content.Context
import android.content.SharedPreferences

class UidPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCES_NAME = "login_preferences"
        private const val IS_LOGIN_KEY = "is_login"
        private const val UID_KEY = "uid"

        @Volatile
        private var INSTANCE: UidPreferences? = null

        fun getInstance(context: Context): UidPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UidPreferences(context)
                INSTANCE = instance
                instance
            }
        }
    }

    fun saveUid(uid: String) {
        sharedPreferences.edit().apply {
            putBoolean(IS_LOGIN_KEY, true)
            putString(UID_KEY, uid)
            apply()
        }
    }

    fun clearUid() {
        sharedPreferences.edit().clear().apply()
    }

    fun getUid(): String? {
        return sharedPreferences.getString(UID_KEY, null)
    }

}