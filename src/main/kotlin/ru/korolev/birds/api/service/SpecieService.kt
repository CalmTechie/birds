package ru.korolev.birds.api.service

import ru.korolev.birds.entity.dto.request.SpecieCreateRequest
import ru.korolev.birds.entity.dto.request.SpecieEditRequest
import ru.korolev.birds.entity.dto.response.SpecieResponse
import ru.korolev.birds.util.LadenStatus

interface SpecieService {

    fun createSpecie(createRequest: SpecieCreateRequest):LadenStatus<Int>

    fun findSpecie(id:Int):LadenStatus<SpecieResponse>

    fun getAllSpecies():LadenStatus<List<SpecieResponse>>

    fun deleteSpecie(id:Int):LadenStatus<Int>

    fun updateSpecieName(id: Int, editRequest: SpecieEditRequest):LadenStatus<Int>
}