package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.WithdrawalCommand
import com.checkingaccount.application.exceptions.AccountDoesNotExistException
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository

class WithdrawalCommandHandler(
    private val checkingAccountEventStoreRepository: CheckingAccountEventStoreRepository,
    private val checkingAccountRepository: CheckingAccountRepository
) {
    fun handle(cmd: WithdrawalCommand) {
        require(checkingAccountRepository.exists(cmd.accountId)) {
            throw AccountDoesNotExistException("the account ${cmd.accountId} does not exist.")
        }
        val events = checkingAccountEventStoreRepository.retrieve(cmd.accountId)
        val account = CheckingAccount(cmd.accountId, events)
        println("Account with id ${cmd.accountId} initialized successfully")
        checkingAccountEventStoreRepository.save(account.withdraw(cmd.value))
        println ("Checking account with id ${cmd.accountId} had ${cmd.value} withdrawn successfully")
    }
}