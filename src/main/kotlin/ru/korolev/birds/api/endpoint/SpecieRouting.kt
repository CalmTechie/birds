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

            resolveResult(specieService.create(createRequest))
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: return@get idNotSpecified()

            resolveResult(specieService.findById(id))
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete idNotSpecified()

            resolveResult(specieService.delete(id))
        }

        patch("/{id}") {
            val id = call.parameters["id"] ?: return@patch idNotSpecified()
            val specieEdit = call.receive<SpecieEditRequest>()

            resolveResult(specieService.updateName(id, specieEdit))
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.resolveResult(result: LadenStatus<out Any>) {
    if (result.isFailed())
        badRequest(result.getMessage())
    else
        okRequest(result.getBurden())
}

private suspend fun PipelineContext<Unit, ApplicationCall>.idNotSpecified() = call.respondText(
    text = "you need to specify id",
    status = HttpStatusCode.BadRequest
)

private suspend fun PipelineContext<Unit, ApplicationCall>.badRequest(message: String) = call.respondText(
    text = message,
    status = HttpStatusCode.BadRequest
)

private suspend fun PipelineContext<Unit, ApplicationCall>.okRequest(burden: Any) = call.respond(
    message = burden,
    status = HttpStatusCode.OK
)