package com.robpalmol.tribeme.DataBase

import com.robpalmol.tribeme.DataBase.Models.AuthResponse
import com.robpalmol.tribeme.DataBase.Models.CreateEventoDTO
import com.robpalmol.tribeme.DataBase.Models.EventoDTO
import com.robpalmol.tribeme.DataBase.Models.ImageUploadResponse
import com.robpalmol.tribeme.DataBase.Models.Tribe
import com.robpalmol.tribeme.DataBase.Models.TribuDTO
import com.robpalmol.tribeme.DataBase.Models.TribuUpdateDTO
import com.robpalmol.tribeme.DataBase.Models.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("api/eventos")
    suspend fun getAllEvents(): List<EventoDTO>

    @GET("api/eventos/{id}")
    suspend fun getEventById(@Path("id") id: Long): EventoDTO

    @GET("api/tribus/mis-tribus")
    suspend fun getMyTribes(): List<Tribe>

    @POST("api/tribus")
    suspend fun crearTribu(@Body tribu: TribuDTO): Tribe

    @GET("api/eventos/tribu/{id}")
    suspend fun getEventosPorTribu(@Path("id") tribuId: Long): List<EventoDTO>

    @POST("api/eventos")
    suspend fun crearEvento(@Body nuevoEvento: CreateEventoDTO): CreateEventoDTO

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

    @POST("api/eventos/{eventoId}/unirse/{usuarioId}")
    suspend fun unirseAEvento(
        @Path("eventoId") eventoId: Long,
        @Path("usuarioId") usuarioId: Long
    ): Response<Unit>

    @DELETE("api/eventos/{eventoId}/salir/{usuarioId}")
    suspend fun salirDeEvento(
        @Path("eventoId") eventoId: Long,
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

    @Multipart
    @POST("api/tribus/imagen")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ImageUploadResponse>

}
