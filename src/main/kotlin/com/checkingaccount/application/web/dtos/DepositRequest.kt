package com.checkingaccount.application.web.dtos

import com.checkingaccount.domain.Money

data class DepositRequest(
    val value: Money
)