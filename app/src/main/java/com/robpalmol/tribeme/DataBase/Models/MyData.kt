package com.robpalmol.tribeme.DataBase.Models

data class Tribe(
    val tribuId: Int,
    val nombre: String,
    val descripcion: String,
    val tribuCreador: User,
    val fechaCreacion: String,
    val fechaModificacion: String,
    val imagen: String?,
    val usuariosMaximos: Int,
    val tribuPrivada: Boolean,
    val crearEventos: Boolean,
    val categorias: List<String>,
    val miembros: List<User>
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
