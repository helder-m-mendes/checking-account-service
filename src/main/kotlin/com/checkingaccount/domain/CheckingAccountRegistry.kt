package com.checkingaccount.domain

import com.github.guepardoapps.kulid.ULID
import java.time.LocalDateTime

data class CheckingAccountRegistry(
    val taxId: String,
    val name: String,
    val accountId: String = ULID.random(),
    val createdAt: LocalDateTime = LocalDateTime.now()
)