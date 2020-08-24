package com.checkingaccount.application.web.controllers

import com.checkingaccount.application.commands.CreateCheckingAccountCommandHandler
import com.checkingaccount.application.commands.dtos.CreateCheckingAccountCommand
import com.checkingaccount.application.exceptions.DuplicateAccountException
import com.checkingaccount.application.web.dtos.CreateCheckingAccountRequest
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus

class CheckingAccountController(
    private val checkingAccountCommandHandler: CreateCheckingAccountCommandHandler
) {

    fun create(ctx: Context) {
        println("Incoming request to create a new checking account")
        val request = ctx.body<CreateCheckingAccountRequest>()
        val cmd = CreateCheckingAccountCommand(
            request.taxId,
            request.name
        )
        try {
            val accountId = checkingAccountCommandHandler.handle(cmd)
            ctx.status(HttpStatus.CREATED_201)
            ctx.json(accountId)
        } catch (e: DuplicateAccountException) {
            ctx.status(HttpStatus.UNPROCESSABLE_ENTITY_422)
            e.message?.let {
                ctx.json(it)
            }
        } catch (e: Exception) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
            println(e.message)
        }
    }
}