package com.rxxskh.data.user.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.rxxskh.utils.SharedPreferenceProvider
import com.rxxskh.data.user.remote.model.UserData
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun saveUser(userData: UserData) {
        with(sharedPreferences.edit()) {
            val json: String = gson.toJson(userData)
            putString(SharedPreferenceProvider.USER_DATA_PREFS_KEY, json)
            apply()
        }
    }

    suspend fun get(): UserData {
//        val json: String? = sharedPreferences.getString(AppKeys.USER_DATA_PREFS_KEY, null)
//        return if (json != null) {
//            gson.fromJson(json, UserData::class.java)
//        } else {
//            UserData()
//        }
        return UserData(
            user_id = "-NsB--OtzG69fUwbEqMX",
            user_login = "kliktak",
            user_password = "123"
        )
    }
}