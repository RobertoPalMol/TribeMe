package com.robpalmol.tribeme.DataBase.Models

data class Tribe(
    val idTribe: Int,
    val name: String,
    val description: String,
    val maxMembers: Int,
    val photo: String?,
    val privateTribe: Boolean,
    val privateEvent: Boolean,
    val creator: User,
    val creationTime: String
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
