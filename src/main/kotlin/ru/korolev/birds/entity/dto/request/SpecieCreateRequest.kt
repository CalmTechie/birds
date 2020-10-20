package ru.korolev.birds.entity.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class SpecieCreateRequest(val name: String)