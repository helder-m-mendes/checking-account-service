package com.checkingaccount.infrasctructure.datasources

interface DataSource<Database, Session> {
    fun getCheckingAccountDatabase(): Database

    fun getCheckingAccountLedgerDatase(): Database

    fun getSession(): Session
}