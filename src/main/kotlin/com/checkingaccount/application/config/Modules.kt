package com.checkingaccount.application.config

import com.checkingaccount.application.commands.CreateCheckingAccountCommandHandler
import com.checkingaccount.application.web.controllers.CheckingAccountController
import com.checkingaccount.application.web.routes.CheckingAccountRoutes
import com.checkingaccount.infrasctructure.datasources.DataSource
import com.checkingaccount.infrasctructure.datasources.implementations.MongoDataSourceImpl
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository
import com.checkingaccount.infrasctructure.repositories.implementations.CheckingAccountEventStoreRepositoryImpl
import com.checkingaccount.infrasctructure.repositories.implementations.CheckingAccountRepositoryImpl
import com.mongodb.client.MongoDatabase
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val qualifier = StringQualifier("mongo")
object Modules {
    fun modules() = module {
        single { EnvironmentConfig() }
        single<DataSource<MongoDatabase>>(qualifier) { MongoDataSourceImpl(get<EnvironmentConfig>().mongoConnection) }
        single<CheckingAccountRepository> { CheckingAccountRepositoryImpl(get(qualifier)) }
        single<CheckingAccountEventStoreRepository> { CheckingAccountEventStoreRepositoryImpl(get(qualifier)) }
        single {CreateCheckingAccountCommandHandler(get(), get(), get(qualifier)) }
        single { CheckingAccountController(get()) }
        single { CheckingAccountRoutes(get()) }
    }
}