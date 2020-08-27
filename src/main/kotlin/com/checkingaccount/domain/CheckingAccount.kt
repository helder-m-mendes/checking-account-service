package com.checkingaccount.domain

import com.checkingaccount.domain.events.CheckingAccountCreditedEvent
import java.math.BigDecimal

class CheckingAccount(
    val accountId: String
) {
    private var balance: Money? = null

    fun deposit(value: Money) = credit(value * BigDecimal(1.005))

    private fun credit(value: Money) =
        CheckingAccountCreditedEvent(
            accountId,
            value
        )
}