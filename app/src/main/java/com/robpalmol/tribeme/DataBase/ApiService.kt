package com.robpalmol.tribeme.DataBase

import com.robpalmol.tribeme.DataBase.Models.AuthResponse
import com.robpalmol.tribeme.DataBase.Models.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Path

interface ApiService {
    @POST("api/auth/login")
    suspend fun loginUser(@Body user: User): AuthResponse

    @POST("api/auth/signup")
    suspend fun registerUser(@Body user: User): User

    @GET("api/usuarios") // Ruta relativa de tu API
    suspend fun getAllUsers(): List<User>
    @GET("api/usuarios/{id}")
    suspend fun getUserbyId(@Path("id") id: Int): User

}
