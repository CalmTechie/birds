package ru.korolev.birds.database

import io.ktor.config.*
import org.mariadb.jdbc.MariaDbPoolDataSource
import javax.sql.DataSource

class DatasourceConfiguration (applicationConfig: ApplicationConfig) {

    val dataSource: DataSource

    init {
        val host = applicationConfig.property("database.host").getString()
        val database = applicationConfig.property("database.dbname").getString()
        val user = applicationConfig.property("database.user").getString()
        val maxPoolSize = applicationConfig.property("database.maxPoolSize").getString()

        dataSource = MariaDbPoolDataSource("jdbc:mariadb://$host/$database?user=$user&maxPoolSize=$maxPoolSize")
    }
}