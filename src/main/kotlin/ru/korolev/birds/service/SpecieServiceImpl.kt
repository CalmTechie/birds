package ru.korolev.birds.service

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ru.korolev.birds.api.service.SpecieService
import ru.korolev.birds.database.Species
import ru.korolev.birds.entity.dto.request.SpecieCreateRequest
import ru.korolev.birds.entity.dto.request.SpecieEditRequest
import ru.korolev.birds.entity.dto.response.SpecieResponse
import ru.korolev.birds.util.LadenStatus

class SpecieServiceImpl : SpecieService {

    override fun createSpecie(createRequest: SpecieCreateRequest): LadenStatus<Int> {
        val id = try {
            transaction {
                Species.insertAndGetId {
                    it[name] = createRequest.name
                }.value
            }
        } catch (e: ExposedSQLException) {
            return LadenStatus.fail("Failed to create entity cause ${e.message}")
        }
        return LadenStatus.ok(id)
    }

    override fun findSpecie(id: Int): LadenStatus<SpecieResponse> {
        val specie = transaction {
            Species.select { Species.id eq id }
                .map {
                    SpecieResponse(
                        it[Species.id].value,
                        it[Species.name]
                    )
                }.firstOrNull()
        }
        return if (specie != null)
            LadenStatus.ok(specie)
        else
            LadenStatus.fail("Couldn't find specie with specified id: $id")
    }

    override fun getAllSpecies(): LadenStatus<List<SpecieResponse>> {
        val specieList = transaction {
            Species.selectAll()
                .map {
                    SpecieResponse(
                        it[Species.id].value,
                        it[Species.name]
                    )
                }
        }
        return LadenStatus.ok(specieList)
    }

    override fun deleteSpecie(id: Int): LadenStatus<Int> {
        val deletedRows = try {
            transaction {
                Species.deleteWhere {
                    Species.id eq id
                }
            }
        } catch (e: ExposedSQLException) {
            return LadenStatus.fail("Failed to delete entity cause ${e.message}")
        }
        return when (deletedRows) {
            1 -> LadenStatus.ok(id)
            0 -> LadenStatus.fail("Couldn't find specie with specified id: $id")
            else -> LadenStatus.fail("What the fuck just happened")
        }
    }

    override fun updateSpecieName(id: Int, editRequest: SpecieEditRequest): LadenStatus<Int> {
        val updatedRows = try {
            transaction {
                Species.update({ Species.id eq id }) {
                    it[name] = editRequest.name
                }
            }
        } catch (e: ExposedSQLException) {
            return LadenStatus.fail("Failed to update entity cause ${e.message}")
        }
        return when (updatedRows) {
            1 -> LadenStatus.ok(id)
            0 -> LadenStatus.fail("Couldn't find specie with specified id: $id")
            else -> LadenStatus.fail("What the fuck just happened")
        }
    }
}