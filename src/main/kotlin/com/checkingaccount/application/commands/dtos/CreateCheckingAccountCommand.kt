package com.checkingaccount.application.commands.dtos

data class CreateCheckingAccountCommand(
    val taxId: String,
    val name: String
)