package com.checkingaccount.infrasctructure.repositories.implementations

import com.checkingaccount.application.config.ObjectMapper
import com.checkingaccount.application.exceptions.DuplicateAccountException
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.domain.CheckingAccountRegistry
import com.checkingaccount.infrasctructure.datasources.DataSource
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository
import com.mongodb.ErrorCategory
import com.mongodb.MongoWriteException
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import org.bson.Document
import org.litote.kmongo.getCollection

class CheckingAccountRepositoryImpl(
    dataSource: DataSource<MongoDatabase>
): CheckingAccountRepository {
    private val db = dataSource.getCheckingAccountDatabase()
        .getCollection("checking_account_registry").also {
            it.createIndex(Indexes.ascending("tax_id"), IndexOptions().unique(true))
            it.createIndex(Indexes.ascending("account_id"))
        }

    override fun insert(checkingAccount: CheckingAccountRegistry) {
        try {
            db.insertOne(Document.parse(ObjectMapper.mapper.writeValueAsString(checkingAccount)))
        } catch (e: MongoWriteException) {
            if (e.error.category == ErrorCategory.DUPLICATE_KEY) {
                println("could not insert checking account because taxId ${checkingAccount.taxId} was duplicated.")
                throw DuplicateAccountException("An account with taxId = ${checkingAccount.taxId} already exists.")
            }
        }
    }

    override fun exists(accountId: String) = db.find(eq("account_id", accountId)).toList().isNotEmpty()
}