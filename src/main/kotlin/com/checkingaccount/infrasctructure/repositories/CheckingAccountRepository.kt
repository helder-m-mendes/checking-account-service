package com.checkingaccount.infrasctructure.repositories

import com.checkingaccount.domain.CheckingAccount

interface CheckingAccountRepository {
    fun insert(checkingAccount: CheckingAccount)
}