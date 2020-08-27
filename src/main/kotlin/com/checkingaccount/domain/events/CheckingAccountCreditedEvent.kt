package com.checkingaccount.domain.events

import com.checkingaccount.domain.Money

data class CheckingAccountCreditedEvent(
    override val accountId: String,
    override val value: Money
): CheckingAccountEvent(accountId, value, CheckingAccountCreditedEvent::class.java)