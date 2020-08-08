package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.CreateCheckingAccountCommand
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository

class CreateCheckingAccountCommandHandler(
    private val checkingAccountRepository: CheckingAccountRepository,
    private val checkingAccountEventStoreRepository: CheckingAccountEventStoreRepository
) {
    fun handle(cmd: CreateCheckingAccountCommand): String {
        val checkingAccount = CheckingAccount(
            cmd.taxId,
            cmd.name
        )
        checkingAccountRepository.insert(checkingAccount)
        print ("Checking account inserted on repository with id ${checkingAccount.accountId}")
        val event = checkingAccount.create()
        checkingAccountEventStoreRepository.save(event)
        print ("Checking account created event saved on event store")
        return checkingAccount.accountId
    }
}