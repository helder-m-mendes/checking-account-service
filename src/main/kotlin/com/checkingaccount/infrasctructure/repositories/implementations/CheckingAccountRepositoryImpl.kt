package com.checkingaccount.infrasctructure.repositories.implementations

import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.datasources.DataSource
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import org.litote.kmongo.getCollection

class CheckingAccountRepositoryImpl(
    private val dataSource: DataSource<MongoDatabase, ClientSession>
): CheckingAccountRepository {
    private val db = dataSource.getCheckingAccountDatabase()
        .getCollection<CheckingAccount>().also {
            it.createIndex(Indexes.ascending("tax_id"), IndexOptions().unique(true))
        }

    override fun insert(checkingAccount: CheckingAccount) {
        db.insertOne(checkingAccount)
    }
}