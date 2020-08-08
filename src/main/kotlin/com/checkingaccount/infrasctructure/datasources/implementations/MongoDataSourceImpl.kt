package com.checkingaccount.infrasctructure.datasources.implementations

import com.checkingaccount.infrasctructure.datasources.DataSource
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo

class MongoDataSourceImpl: DataSource<MongoDatabase, ClientSession> {
    private val mongoClient = KMongo.createClient()
    private val checkingAccountDb: MongoDatabase =
        mongoClient.getDatabase("checking_account")
    private val checkingAccountLedgerDb: MongoDatabase =
        mongoClient.getDatabase("checking_account_ledger")

    fun startTransaction() {
        mongoClient.startSession().startTransaction()
    }

    override fun getCheckingAccountDatabase() = checkingAccountDb

    override fun getCheckingAccountLedgerDatase() = checkingAccountLedgerDb

    override fun getSession() = mongoClient.startSession()
}