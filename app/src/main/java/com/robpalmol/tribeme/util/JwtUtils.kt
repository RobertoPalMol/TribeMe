package com.robpalmol.tribeme.util

import android.util.Base64
import com.robpalmol.tribeme.DataBase.Models.User
import org.json.JSONObject

object JwtUtils {
    fun decodeToken(token: String): User? {
        try {
            val parts = token.split(".")
            if (parts.size != 3) {
                throw IllegalArgumentException("Token mal formado")
            }

            val payload = parts[1]
            val decodedPayload = String(Base64.decode(payload, Base64.URL_SAFE))

            val json = JSONObject(decodedPayload)

            val usuarioId = json.optLong("usuarioId")
            val nombre = json.optString("nombre")
            val correo = json.optString("correo")
            val fechaCreacion = json.optString("fechaCreacion")

            // Creamos y retornamos el objeto User con la información
            return User(usuarioId, nombre, correo, "", fechaCreacion)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
