package com.checkingaccount.infrasctructure.repositories

import com.checkingaccount.domain.CheckingAccountRegistry

interface CheckingAccountRepository {
    fun insert(checkingAccount: CheckingAccountRegistry)
}