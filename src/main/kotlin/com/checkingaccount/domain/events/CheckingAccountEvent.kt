package com.checkingaccount.domain.events

import com.checkingaccount.domain.Money
import java.time.LocalDateTime

sealed class CheckingAccountEvent(
    open val accountId: String,
    open val value: Money,
    val type: Class<*>,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

data class CheckingAccountCreditedEvent(
    override val accountId: String,
    override val value: Money
): CheckingAccountEvent(accountId, value, CheckingAccountCreditedEvent::class.java)

data class CheckingAccountDebitedEvent(
    override val accountId: String,
    override val value: Money
): CheckingAccountEvent(accountId, value, CheckingAccountDebitedEvent::class.java)

data class CheckingAccountTransferredEvent(
    override val accountId: String,
    override val value: Money,
    val toAccount: String
): CheckingAccountEvent(accountId, value, CheckingAccountTransferredEvent::class.java)
