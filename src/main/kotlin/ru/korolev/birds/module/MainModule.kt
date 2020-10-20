package ru.korolev.birds.module

import io.ktor.application.*
import org.koin.core.KoinApplication
import org.koin.dsl.module
import ru.korolev.birds.api.service.SpecieService
import ru.korolev.birds.database.DatasourceConfiguration
import ru.korolev.birds.database.SessionFactory
import ru.korolev.birds.service.SpecieServiceImpl

fun KoinApplication.environmentalModules(environment: ApplicationEnvironment): KoinApplication {
    val specieModule = module {
        single { DatasourceConfiguration(environment.config) }
        single { SessionFactory(get()) }
        single<SpecieService> { SpecieServiceImpl(get()) }
    }
    return modules(listOf(specieModule))
}