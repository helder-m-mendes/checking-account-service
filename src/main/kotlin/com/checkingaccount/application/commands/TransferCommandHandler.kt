package com.checkingaccount.application.commands

import com.checkingaccount.application.commands.dtos.TransferCommand
import com.checkingaccount.application.exceptions.AccountDoesNotExistException
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository

class TransferCommandHandler(
    private val checkingAccountEventStoreRepository: CheckingAccountEventStoreRepository,
    private val checkingAccountRepository: CheckingAccountRepository
) {
    fun handle(cmd: TransferCommand) {
        require(checkingAccountRepository.exists(cmd.fromAccount)) {
            throw AccountDoesNotExistException("the account ${cmd.fromAccount} does not exist.")
        }
        require(checkingAccountRepository.exists(cmd.toAccount)) {
            throw AccountDoesNotExistException("the account ${cmd.toAccount} does not exist.")
        }
        val events = checkingAccountEventStoreRepository.retrieve(cmd.fromAccount)
        val account = CheckingAccount(cmd.fromAccount, events)
        println("Account with id ${cmd.fromAccount} initialized successfully")
        checkingAccountEventStoreRepository.save(account.transfer(cmd.value, cmd.toAccount))
        println ("Checking account with id ${cmd.fromAccount} had " +
                "${cmd.value} transferred to ${cmd.toAccount} successfully")
    }
}