package com.checkingaccount.application.web.dtos

import com.checkingaccount.domain.Money

data class TransferRequest(
    val value: Money,
    val accountId: String
)