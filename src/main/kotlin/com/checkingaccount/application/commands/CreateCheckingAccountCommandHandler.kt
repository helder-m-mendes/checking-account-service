package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.CreateCheckingAccountCommand
import com.checkingaccount.domain.CheckingAccountRegistry
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository

class CreateCheckingAccountCommandHandler(
    private val checkingAccountRepository: CheckingAccountRepository
) {
    fun handle(cmd: CreateCheckingAccountCommand): String {
        val checkingAccountRegistry = CheckingAccountRegistry(
            cmd.taxId,
            cmd.name
        )

        checkingAccountRepository.insert(checkingAccountRegistry)
        println ("Checking account with id ${checkingAccountRegistry.accountId}, taxId ${checkingAccountRegistry.taxId}" +
                " and name ${checkingAccountRegistry.name} inserted on the repository")
        return checkingAccountRegistry.accountId
    }
}