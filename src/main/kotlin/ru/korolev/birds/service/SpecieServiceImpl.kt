package ru.korolev.birds.service

import ru.korolev.birds.api.service.SpecieService
import ru.korolev.birds.database.SessionFactory
import ru.korolev.birds.database.mapper.SpecieMapper
import ru.korolev.birds.entity.Specie
import ru.korolev.birds.entity.dto.request.SpecieCreateRequest
import ru.korolev.birds.entity.dto.request.SpecieEditRequest
import ru.korolev.birds.util.LadenStatus
import java.util.*

class SpecieServiceImpl(sessionFactory: SessionFactory) : SpecieService, AbstractService(sessionFactory) {

    override fun create(createRequest: SpecieCreateRequest): LadenStatus<Specie> =
        sessionFactory.openSession().use {
            return try {
                val id = UUID.randomUUID().toString()
                val specie = Specie(id, createRequest.name)
                it.getMapper(SpecieMapper::class.java).save(specie)
                LadenStatus.ok(specie)
            } catch (e: RuntimeException) {
                LadenStatus.fail("Failed to create entity cause ${e.message}")
            }
        }

    override fun findById(id: String): LadenStatus<Specie> =
        sessionFactory.openSession().use {
            val specie = it.getMapper(SpecieMapper::class.java).findById(id)
            return if (specie != null)
                LadenStatus.ok(specie)
            else
                LadenStatus.fail("Couldn't find specie with specified id: $id")
        }

    override fun delete(id: String): LadenStatus<String> =
        sessionFactory.openSession().use {
            return try {
                it.getMapper(SpecieMapper::class.java).delete(id)
                LadenStatus.ok(id)
            } catch (e: RuntimeException) {
                LadenStatus.fail("Failed to delete entity cause ${e.message}")
            }
        }

    override fun updateName(id: String, editRequest: SpecieEditRequest): LadenStatus<Specie> =
        sessionFactory.openSession().use {
            return try {
                val mapper = it.getMapper(SpecieMapper::class.java)
                val specie =
                    mapper.findById(id) ?: return LadenStatus.fail("Couldn't find specie with specified id: $id")

                specie.name = editRequest.name
                mapper.update(specie)

                LadenStatus.ok(specie)
            } catch (e: RuntimeException) {
                LadenStatus.fail("Failed to update entity cause ${e.message}")
            }
        }
}