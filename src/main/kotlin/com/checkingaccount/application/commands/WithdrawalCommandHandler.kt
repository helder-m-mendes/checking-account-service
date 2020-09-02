package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.WithdrawalCommand
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository

class WithdrawalCommandHandler(
    private val checkingAccountEventStoreRepository: CheckingAccountEventStoreRepository
) {
    fun handle(cmd: WithdrawalCommand) {
        val events = checkingAccountEventStoreRepository.retrieve(cmd.accountId)
        val account = CheckingAccount(cmd.accountId, events)
        println("Account with id ${cmd.accountId} initialized successfully")
        checkingAccountEventStoreRepository.save(account.withdraw(cmd.value))
        println ("Checking account with id ${cmd.accountId} had ${cmd.value} withdrawn successfully")
    }
}