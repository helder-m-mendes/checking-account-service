package com.checkingaccount.application.web.dtos

data class CreateCheckingAccountRequest(
    val taxId: String,
    val name: String
)