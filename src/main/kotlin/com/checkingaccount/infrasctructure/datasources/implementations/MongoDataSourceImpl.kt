package com.checkingaccount.infrasctructure.datasources.implementations

import com.checkingaccount.infrasctructure.datasources.DataSource
import com.mongodb.ReadConcern
import com.mongodb.TransactionOptions
import com.mongodb.WriteConcern
import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo

class MongoDataSourceImpl: DataSource<MongoDatabase> {
    private val mongoClient = KMongo.createClient("mongodb+srv://checking_account_service:checking_account_service@cluster0.xhuux.mongodb.net/<dbname>?retryWrites=true&w=majority")
    private val checkingAccountDb: MongoDatabase =
        mongoClient.getDatabase("checking_account")
    private val checkingAccountLedgerDb: MongoDatabase =
        mongoClient.getDatabase("checking_account_ledger")
    private val session = mongoClient.startSession()

    override fun getCheckingAccountDatabase() = checkingAccountDb

    override fun getCheckingAccountLedgerDatase() = checkingAccountLedgerDb

    override fun startTransaction() {
        session.startTransaction(TransactionOptions.builder().readConcern(ReadConcern.SNAPSHOT).writeConcern(WriteConcern.MAJORITY).build())
    }

    override fun abortTransaction() {
        session.abortTransaction()
    }

    override fun endTransaction() {
        session.commitTransaction()
    }
}