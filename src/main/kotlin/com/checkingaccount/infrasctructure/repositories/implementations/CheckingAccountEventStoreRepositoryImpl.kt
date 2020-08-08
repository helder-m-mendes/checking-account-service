package com.checkingaccount.infrasctructure.repositories.implementations

import com.checkingaccount.domain.events.CheckingAccountEvent
import com.checkingaccount.infrasctructure.datasources.DataSource
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.mongodb.client.ClientSession
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Indexes
import org.litote.kmongo.getCollection

class CheckingAccountEventStoreRepositoryImpl(
    private val dataSource: DataSource<MongoDatabase, ClientSession>
): CheckingAccountEventStoreRepository {
    private val db = dataSource.getCheckingAccountLedgerDatase()
        .getCollection<CheckingAccountEvent>().also {
            it.createIndex(Indexes.ascending("account_id"))
            it.createIndex(Indexes.ascending("created_at"))
        }

    override fun save(event: CheckingAccountEvent) {
        db.insertOne(event)
    }
}