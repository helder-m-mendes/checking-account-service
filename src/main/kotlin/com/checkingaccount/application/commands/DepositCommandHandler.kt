package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.DepositCommand
import com.checkingaccount.application.exceptions.AccountDoesNotExistException
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository

class DepositCommandHandler(
    private val checkingAccountEventStoreRepository: CheckingAccountEventStoreRepository,
    private val checkingAccountRepository: CheckingAccountRepository
) {
    fun handle(cmd: DepositCommand) {
        require(checkingAccountRepository.exists(cmd.accountId)) {
            throw AccountDoesNotExistException("the account ${cmd.accountId} does not exist.")
        }
        val checkingAccount = CheckingAccount(cmd.accountId)
        checkingAccountEventStoreRepository.save(checkingAccount.deposit(cmd.value))
        println ("Checking account with id ${cmd.accountId} deposited with value ${cmd.value} successfully")
    }
}