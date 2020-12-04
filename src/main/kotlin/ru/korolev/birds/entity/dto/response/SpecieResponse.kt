package ru.korolev.birds.entity.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class SpecieResponse(val id: Int, val name: String)