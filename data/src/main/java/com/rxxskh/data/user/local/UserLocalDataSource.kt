package com.rxxskh.data.user.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxxskh.domain.user.model.User
import com.rxxskh.utils.SharedPreferenceProvider
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun saveUser(user: User) {
        with(sharedPreferences.edit()) {
            val json: String = gson.toJson(user)
            putString(SharedPreferenceProvider.USER_DATA_PREFS_KEY, json)
            apply()
        }
    }

    suspend fun getUser(): User? {
        val json: String? =
            sharedPreferences.getString(SharedPreferenceProvider.USER_DATA_PREFS_KEY, null)
        val result = if (json != null) {
            gson.fromJson(json, User::class.java)
        } else {
            null
        }
        return result
    }

    suspend fun clearUser() {
        sharedPreferences.edit().remove(SharedPreferenceProvider.USER_DATA_PREFS_KEY).apply()
    }
}