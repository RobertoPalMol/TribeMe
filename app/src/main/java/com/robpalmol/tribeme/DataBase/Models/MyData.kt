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
    val id: Long,
    val name: String,
    val mail: String,
    val password: String,
    val creationTime: String
)