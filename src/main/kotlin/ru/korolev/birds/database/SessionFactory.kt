package ru.korolev.birds.database

import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import ru.korolev.birds.database.mapper.SpecieMapper

class SessionFactory(datasourceConfiguration: DatasourceConfiguration) {

    private val sqlSessionFactory: SqlSessionFactory;

    init {
        val environment = Environment("development", JdbcTransactionFactory(), datasourceConfiguration.dataSource)
        val configuration = Configuration(environment)
        configuration.addMapper(SpecieMapper::class.java)
        sqlSessionFactory = SqlSessionFactoryBuilder().build(configuration)
    }

    fun openSession(): SqlSession = sqlSessionFactory.openSession(true)
}