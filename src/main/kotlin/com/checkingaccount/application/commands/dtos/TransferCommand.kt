package com.checkingaccount.application.commands.dtos

import com.checkingaccount.application.exceptions.InvalidDataException
import com.checkingaccount.domain.Money

data class TransferCommand(
    val fromAccount: String,
    val toAccount: String,
    val value: Money
) {
    init {
        require(fromAccount != toAccount) {
            throw InvalidDataException("the targeted account should be different.")
        }
    }
}