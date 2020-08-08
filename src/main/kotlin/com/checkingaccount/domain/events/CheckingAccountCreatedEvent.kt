package com.checkingaccount.domain.events

import com.checkingaccount.domain.CheckingAccount

data class CheckingAccountCreatedEvent(
    override val checkingAccount: CheckingAccount
): CheckingAccountEvent(checkingAccount)