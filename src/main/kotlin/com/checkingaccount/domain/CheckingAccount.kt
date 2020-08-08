package com.checkingaccount.domain

import com.checkingaccount.domain.events.CheckingAccountCreatedEvent
import com.github.guepardoapps.kulid.ULID

class CheckingAccount(
    val taxId: String,
    val name: String,
    val accountId: String = ULID.random()
) {
    private var balance: Money? = null

    fun create()=
        CheckingAccountCreatedEvent(
            this)
}