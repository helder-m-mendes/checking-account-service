package com.checkingaccount.domain

import com.checkingaccount.domain.events.CheckingAccountCreatedEvent

class CheckingAccount(
    val accountId: String
) {
    private var balance: Money = Money(0.0)
}