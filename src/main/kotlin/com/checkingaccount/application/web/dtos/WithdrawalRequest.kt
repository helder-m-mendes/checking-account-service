package com.checkingaccount.application.web.dtos

import com.checkingaccount.domain.Money

data class WithdrawalRequest(
    val value: Money
)