package ru.korolev.birds.service

import ru.korolev.birds.database.SessionFactory

abstract class AbstractService(protected val sessionFactory: SessionFactory)