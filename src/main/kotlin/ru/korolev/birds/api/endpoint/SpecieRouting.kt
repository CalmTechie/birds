package ru.korolev.birds.api.endpoint

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject
import ru.korolev.birds.api.service.SpecieService
import ru.korolev.birds.entity.dto.request.SpecieCreateRequest
import ru.korolev.birds.entity.dto.request.SpecieEditRequest
import ru.korolev.birds.util.LadenStatus

fun Routing.specie() {

    val specieService: SpecieService by inject()

    route("specie") {
        post("/") {
            val createRequest = call.receive<SpecieCreateRequest>()

            val createResult = specieService.createSpecie(createRequest)
            respond(createResult)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@get respondIdNotSpecified()

            val findResult = specieService.findSpecie(id)
            respond(findResult)
        }

        get("/") {
            val findAllResult = specieService.getAllSpecies()
            respond(findAllResult)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete respondIdNotSpecified()

            val deleteResult = specieService.deleteSpecie(id)
            respond(deleteResult)
        }

        patch("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: return@patch respondIdNotSpecified()
            val specieEdit = call.receive<SpecieEditRequest>()

            val editResult = specieService.updateSpecieName(id, specieEdit)
            respond(editResult)
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.respond(result: LadenStatus<out Any>) {
    if (result.isFailed())
        respondFail(result.getMessage())
    else
        respondOk(result.getBurden())
}

private suspend fun PipelineContext<Unit, ApplicationCall>.respondOk(burden: Any) = call.respond(
    message = burden,
    status = HttpStatusCode.OK
)

private suspend fun PipelineContext<Unit, ApplicationCall>.respondFail(message: String) = call.respondText(
    text = message,
    status = HttpStatusCode.BadRequest //todo change
)

private suspend fun PipelineContext<Unit, ApplicationCall>.respondIdNotSpecified() = call.respondText(
    text = "You need to specify id",
    status = HttpStatusCode.BadRequest
)
