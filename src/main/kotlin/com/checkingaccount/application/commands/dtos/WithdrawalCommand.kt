package com.checkingaccount.application.commands.dtos

import com.checkingaccount.domain.Money

data class WithdrawalCommand(
    val accountId: String,
    val value: Money
)