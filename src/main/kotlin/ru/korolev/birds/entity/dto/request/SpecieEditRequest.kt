package ru.korolev.birds.entity.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SpecieEditRequest(val name: String)