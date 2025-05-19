package com.robpalmol.tribeme.DataBase.Models


data class Tribe(
    val tribuId: Long,
    val nombre: String,
    val descripcion: String,
    val imagenUrl: String?,
    val categorias: List<String>,
    val numeroMaximoMiembros: Int,
    val esPrivada: Boolean,
    val fechaCreacion: String,
    val autorId: String,
    val autorNombre: String,
    val miembros: List<User>
)
data class TribuDTO(
    val nombre: String,
    val descripcion: String,
    val imagenUrl: String,
    val categorias: List<String>,
    val numeroMaximoMiembros: Int,
    val esPrivada: Boolean,
    val autorId: Long
)

data class CreateTribeRequest(
    val nombre: String,
    val descripcion: String,
    val categorias: List<String>,
    val imagenUrl: String,
    val numeroMaximoMiembros: Int,
    val esPrivada: Boolean
)


data class User(
    val usuarioId: Long,
    val nombre: String,
    val correo: String,
    val contraseña: String,
    val fechaCreacion: String
)

data class AuthResponse(
    val token: String,
    val user: User
)

data class EventoDTO(
    val eventoId: Long,
    val nombre: String,
    val descripcion: String?,
    val hora: String,
    val lugar: String,
    val fechaCreacion: String,
    val fechaModificacion: String,
    val creadorId: Long
)

