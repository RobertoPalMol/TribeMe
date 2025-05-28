package com.robpalmol.tribeme.DataBase

import android.content.Context
import com.robpalmol.tribeme.util.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    // Backend URLs

    //Local
    const val BASE_URL = "http://192.168.0.33:8081"

    //render
    //private const val BASE_URL = "https://tribeme-backend.onrender.com"

    //azure mv
    //private const val BASE_URL = "http://20.84.117.63:8081"

    //portatil back
    //const val BASE_URL = "http://192.168.0.31:8081"

    //railway
    //private const val BASE_URL = "https://tribemebackend-production.up.railway.app"

    //aws vm
    //const val BASE_URL ="http://56.228.33.92:8081"

    // Inicializar Retrofit con el Contexto disponible
    fun getRetrofitInstance(context: Context): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val requestBuilder = originalRequest.newBuilder()
                        // Obtener el token de sesión guardado
                        val token = SessionManager(context).getToken()
                        if (!token.isNullOrEmpty()) {
                            requestBuilder.addHeader("Authorization", "Bearer $token")
                        }

                        val requestWithToken = requestBuilder.build()
                        chain.proceed(requestWithToken)
                    }
                    .build()
            )
            .build()
    }

    // Proporcionar la instancia de la API con Retrofit
    fun getApiService(context: Context): ApiService {
        return getRetrofitInstance(context).create(ApiService::class.java)
    }
}