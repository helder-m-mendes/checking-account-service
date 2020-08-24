package com.checkingaccount.domain

import com.checkingaccount.domain.events.CheckingAccountCreatedEvent
import com.github.guepardoapps.kulid.ULID
import java.time.LocalDateTime

class CheckingAccount(
    val taxId: String,
    val name: String,
    val accountId: String = ULID.random(),
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    private var balance: Money? = null

    fun create()=
        CheckingAccountCreatedEvent(
            this.accountId)
}