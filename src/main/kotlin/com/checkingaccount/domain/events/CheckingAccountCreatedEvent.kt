package com.checkingaccount.domain.events

data class CheckingAccountCreatedEvent(
    override val accountId: String
): CheckingAccountEvent(accountId)