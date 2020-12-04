package ru.korolev.birds.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Species: IntIdTable(name = "specie") {
    val name: Column<String> = varchar("name", 100).uniqueIndex()
}