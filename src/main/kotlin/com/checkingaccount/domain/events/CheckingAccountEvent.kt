package com.checkingaccount.domain.events

import java.time.LocalDateTime

open class CheckingAccountEvent(
    open val accountId: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)