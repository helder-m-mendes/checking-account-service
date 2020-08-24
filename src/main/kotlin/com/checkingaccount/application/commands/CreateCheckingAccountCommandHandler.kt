package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.CreateCheckingAccountCommand
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.datasources.DataSource
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository
import com.mongodb.client.MongoDatabase

class CreateCheckingAccountCommandHandler(
    private val checkingAccountRepository: CheckingAccountRepository,
    private val checkingAccountEventStoreRepository: CheckingAccountEventStoreRepository,
    private val dataSource: DataSource<MongoDatabase>
) {
    fun handle(cmd: CreateCheckingAccountCommand): String {
        val checkingAccount = CheckingAccount(
            cmd.taxId,
            cmd.name
        )
        dataSource.startTransaction()
        try {
            checkingAccountRepository.insert(checkingAccount)
            println ("Checking account with id ${checkingAccount.accountId} and taxId ${checkingAccount.taxId}" +
                    " inserted on the repository")
            val event = checkingAccount.create()
            checkingAccountEventStoreRepository.save(event)
            println ("Checking account event $event saved on event store")
            dataSource.endTransaction()
            return checkingAccount.accountId
        } catch (e: Exception) {
            dataSource.abortTransaction()
            throw  e
        }
    }
}