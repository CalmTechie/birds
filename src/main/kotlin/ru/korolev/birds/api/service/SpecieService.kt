package ru.korolev.birds.api.service

import ru.korolev.birds.entity.Specie
import ru.korolev.birds.entity.dto.request.SpecieCreateRequest
import ru.korolev.birds.entity.dto.request.SpecieEditRequest
import ru.korolev.birds.util.LadenStatus

interface SpecieService {

    fun create(createRequest: SpecieCreateRequest):LadenStatus<Specie>

    fun findById(id:String):LadenStatus<Specie>

    fun delete(id:String):LadenStatus<String>

    fun updateName(id: String, editRequest: SpecieEditRequest):LadenStatus<Specie>
}