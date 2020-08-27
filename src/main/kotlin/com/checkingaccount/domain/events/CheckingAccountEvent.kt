package com.checkingaccount.domain.events

import com.checkingaccount.domain.Money
import java.time.LocalDateTime

open class CheckingAccountEvent(
    open val accountId: String,
    open val value: Money,
    val type: Class<*>,
    val createdAt: LocalDateTime = LocalDateTime.now()
)