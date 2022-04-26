package ru.ssau.reviewzor.data

import android.content.Context
import android.content.SharedPreferences
import ru.ssau.reviewzor.StorageConstant

class TokenRepository(private val context: Context) {

    fun saveToken(token: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            StorageConstant.NAME_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()

        editor.putString(StorageConstant.NAME_TOKEN_STORAGE, token)
        editor.apply()
    }

    fun getToken() =
        context.getSharedPreferences(StorageConstant.NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .getString(StorageConstant.NAME_TOKEN_STORAGE, "")

    fun deleteToken(){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            StorageConstant.NAME_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()

        editor.clear()
        editor.apply()
    }

}