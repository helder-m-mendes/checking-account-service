package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.DepositCommand
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository

class DepositCommandHandler(
    private val checkingAccountEventStoreRepository: CheckingAccountEventStoreRepository
) {
    fun handle(cmd: DepositCommand) {
        val checkingAccount = CheckingAccount(cmd.accountId)
        checkingAccountEventStoreRepository.save(checkingAccount.deposit(cmd.value))
        println ("Checking account with id ${cmd.accountId} deposited with value ${cmd.value} successfully")
    }
}