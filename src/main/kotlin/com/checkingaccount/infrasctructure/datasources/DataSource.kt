package com.checkingaccount.infrasctructure.datasources

interface DataSource<Database> {
    fun getCheckingAccountDatabase(): Database

    fun getCheckingAccountLedgerDatase(): Database

    fun startTransaction()

    fun abortTransaction()

    fun endTransaction()
}