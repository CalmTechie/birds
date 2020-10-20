package ru.korolev.birds.entity

import kotlinx.serialization.Serializable

@Serializable
data class Specie(
    val id: String = "",
    var name: String = ""
)