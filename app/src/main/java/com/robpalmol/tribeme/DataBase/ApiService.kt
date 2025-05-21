package com.robpalmol.tribeme.DataBase

import com.robpalmol.tribeme.DataBase.Models.AuthResponse
import com.robpalmol.tribeme.DataBase.Models.EventoDTO
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.TribuDTO
import com.robpalmol.tribeme.DataBase.Models.TribuUpdateDTO
import com.robpalmol.tribeme.DataBase.Models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/auth/login")
    suspend fun loginUser(@Body user: User): AuthResponse

    @POST("api/auth/signup")
    suspend fun registerUser(@Body user: User): AuthResponse

    @GET("api/usuarios")
    suspend fun getAllUsers(): List<User>

    @GET("api/usuarios/{id}")
    suspend fun getUserById(@Path("id") id: Long): User

    @GET("api/tribus")
    suspend fun getAllTribes(): List<Tribe>

    @POST("api/tribus")
    suspend fun crearTribu(@Body tribu: TribuDTO): Tribe

    @GET("api/eventos")
    suspend fun getAllEventos(): List<EventoDTO>

    @POST("api/tribus/{tribuId}/unirse/{usuarioId}")
    suspend fun unirseATribu(
        @Path("tribuId") tribuId: Long,
        @Path("usuarioId") usuarioId: Long
    ): Response<Unit>

    @DELETE("api/tribus/{tribuId}/salir/{usuarioId}")
    suspend fun salirDeTribu(
        @Path("tribuId") tribuId: Long,
        @Path("usuarioId") usuarioId: Long
    ): Response<Unit>

    @GET("api/tribus/filtrar")
    suspend fun buscarTribus(
        @Query("nombre") nombre: String? = null,
        @Query("ubicacion") ubicacion: String? = null,
        @Query("categorias") categorias: List<String>? = null
    ): List<Tribe>

    @PUT("api/tribus/{id}")
    suspend fun updateTribe(
        @Path("id") tribeId: Long,
        @Body request: TribuUpdateDTO
    ): Response<Void>

    @DELETE("api/tribus/{id}")
    suspend fun deleteTribu(
        @Path("id") tribuId: Long
    ): Response<Void>

}
