package com.robpalmol.tribeme.DataBase

import android.content.Context
import com.robpalmol.tribeme.util.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    // Backend URLs

    //Local
    private const val BASE_URL = "http://192.168.0.19:8081"

    //render
    //private const val BASE_URL = "https://tribeme-backend.onrender.com"

    //azure mv
    //private const val BASE_URL = "http://20.84.117.63:8081"

    //portatil back
    //private const val BASE_URL = "http://192.168.0.31:8081"

    // Inicializar Retrofit con el Contexto disponible
    fun getRetrofitInstance(context: Context): Retrofit {
        // Obtener el token de sesión guardado
        val token = SessionManager(context).getToken()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val requestWithToken = originalRequest.newBuilder()
                            .apply {
                                token?.let {
                                    header("Authorization", "Bearer $it") // Agregar el token en las cabeceras
                                }
                            }
                            .build()
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