package com.checkingaccount.infrasctructure.repositories.implementations

import com.checkingaccount.application.config.ObjectMapper
import com.checkingaccount.domain.events.CheckingAccountEvent
import com.checkingaccount.infrasctructure.datasources.DataSource
import com.checkingaccount.infrasctructure.repositories.CheckingAccountEventStoreRepository
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.*
import com.mongodb.client.model.Indexes
import org.bson.Document

class CheckingAccountEventStoreRepositoryImpl(
    dataSource: DataSource<MongoDatabase>
): CheckingAccountEventStoreRepository {
    private val db = dataSource.getCheckingAccountLedgerDatabase()
        .getCollection("checking_account_ledger").also {
            it.createIndex(Indexes.ascending("account_id"))
            it.createIndex(Indexes.ascending("created_at"))
        }

    override fun save(event: CheckingAccountEvent) {
        db.insertOne(Document.parse(ObjectMapper.mapper.writeValueAsString(event)))
    }

    override fun retrieve(accountId: String)  =
        db.find(or(eq("account_id", accountId),
            eq("to_account", accountId))).toList().map { document ->
            CheckingAccountEvent::class.sealedSubclasses.firstOrNull {
                document["type"] == it.qualifiedName
            }.let {
                ObjectMapper.mapper.readValue(document.toJson(),it?.java)
            }
        }
}

