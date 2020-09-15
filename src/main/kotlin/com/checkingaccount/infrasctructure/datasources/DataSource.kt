package com.checkingaccount.infrasctructure.datasources

interface DataSource<Database> {
    fun getCheckingAccountDatabase(): Database

    fun getCheckingAccountLedgerDatabase(): Database
}