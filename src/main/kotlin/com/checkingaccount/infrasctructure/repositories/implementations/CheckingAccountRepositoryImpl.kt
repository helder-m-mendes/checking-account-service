package com.checkingaccount.infrasctructure.repositories.implementations

import com.checkingaccount.application.exceptions.DuplicateAccountException
import com.checkingaccount.domain.CheckingAccount
import com.checkingaccount.infrasctructure.datasources.DataSource
import com.checkingaccount.infrasctructure.repositories.CheckingAccountRepository
import com.mongodb.ErrorCategory
import com.mongodb.MongoWriteException
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import org.litote.kmongo.getCollection

class CheckingAccountRepositoryImpl(
    dataSource: DataSource<MongoDatabase>
): CheckingAccountRepository {
    private val db = dataSource.getCheckingAccountDatabase()
        .getCollection<CheckingAccount>().also {
            it.createIndex(Indexes.ascending("taxId"), IndexOptions().unique(true))
        }

    override fun insert(checkingAccount: CheckingAccount) {
        try {
            db.insertOne(checkingAccount)
        } catch (e: MongoWriteException) {
            if (e.error.category == ErrorCategory.DUPLICATE_KEY) {
                throw DuplicateAccountException("An account with taxId = ${checkingAccount.taxId} already exists.")
            }
        }
    }
}