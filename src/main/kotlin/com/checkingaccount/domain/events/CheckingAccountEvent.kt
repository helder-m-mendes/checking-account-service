package com.checkingaccount.domain.events

import com.checkingaccount.domain.CheckingAccount

open class CheckingAccountEvent(
    open val checkingAccount: CheckingAccount
)