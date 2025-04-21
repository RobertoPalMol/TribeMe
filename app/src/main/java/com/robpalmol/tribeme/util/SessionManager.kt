package com.robpalmol.tribeme.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    // Guardar token
    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("TOKEN", token)
        editor.apply()
    }

    // Obtener token
    fun getToken(): String? {
        return sharedPreferences.getString("TOKEN", null)
    }
}
