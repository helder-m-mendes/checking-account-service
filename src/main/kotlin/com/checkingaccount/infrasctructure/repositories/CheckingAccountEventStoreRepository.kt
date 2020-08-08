package com.checkingaccount.infrasctructure.repositories

import com.checkingaccount.domain.events.CheckingAccountEvent

interface CheckingAccountEventStoreRepository {
    fun save(event: CheckingAccountEvent)
}