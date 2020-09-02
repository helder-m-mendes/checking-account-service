package com.checkingaccount.domain

import com.checkingaccount.application.exceptions.InsufficientFundsException
import com.checkingaccount.domain.events.CheckingAccountCreditedEvent
import com.checkingaccount.domain.events.CheckingAccountDebitedEvent
import com.checkingaccount.domain.events.CheckingAccountEvent
import java.math.BigDecimal

class CheckingAccount(
    private val accountId: String
) {
    private lateinit var balance: Money

    constructor(accountId: String, events: List<CheckingAccountEvent>) : this(accountId) {
        balance = Money(BigDecimal(0))
        events.forEach {
            when (it) {
                is CheckingAccountCreditedEvent -> balance += it.value
                is CheckingAccountDebitedEvent -> balance -= it.value
            }
        }
    }

    fun deposit(value: Money) = credit(value * BigDecimal(1.005))

    fun withdraw(value: Money) = debit(value * BigDecimal(1.01))

    private fun credit(value: Money) =
        CheckingAccountCreditedEvent(
            accountId,
            value
        )

    private fun debit(value: Money) =
        if (balance - value > Money(BigDecimal(0))) {
            CheckingAccountDebitedEvent(
                accountId,
                value
            )
        } else {
            throw  InsufficientFundsException(
                "Insufficient funds to cover the desired debit." +
                        "Available balance: ${balance.asBigDecimal()}"
            )
        }
}