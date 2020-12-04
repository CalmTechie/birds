package ru.korolev.birds.database

import io.ktor.config.*
import org.jetbrains.exposed.sql.Database
import org.mariadb.jdbc.MariaDbPoolDataSource

class DatasourceConfiguration(applicationConfig: ApplicationConfig) {

    init {
        val host = applicationConfig.property("database.host").getString()
        val database = applicationConfig.property("database.dbname").getString()
        val user = applicationConfig.property("database.user").getString()
        val maxPoolSize = applicationConfig.property("database.maxPoolSize").getString()

        val dataSource =
            MariaDbPoolDataSource("jdbc:mariadb://$host/$database?user=$user&maxPoolSize=$maxPoolSize&minPoolSize=1")

        Database.connect(getNewConnection = { dataSource.connection })
    }
}