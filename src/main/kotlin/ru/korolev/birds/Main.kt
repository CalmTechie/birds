package ru.korolev.birds

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.SerializationException
import org.koin.ktor.ext.Koin
import ru.korolev.birds.api.endpoint.specie
import ru.korolev.birds.module.environmentalModules

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Koin) {
        environmentalModules(environment)
    }
    install(StatusPages) {
        exception<ContentTransformationException> {
            call.respond(HttpStatusCode.BadRequest)
        }
        exception<SerializationException> {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
    install(ContentNegotiation) {
        json()
    }
    install(Routing) {
        specie()
    }
}