package com.checkingaccount.application.web.controllers

import com.checkingaccount.application.commands.CreateCheckingAccountCommandHandler
import com.checkingaccount.application.commands.DepositCommandHandler
import com.checkingaccount.application.commands.dtos.CreateCheckingAccountCommand
import com.checkingaccount.application.commands.dtos.DepositCommand
import com.checkingaccount.application.exceptions.DuplicateAccountException
import com.checkingaccount.application.web.dtos.CreateCheckingAccountRequest
import com.checkingaccount.application.web.dtos.DepositRequest
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus

class CheckingAccountController(
    private val createCheckingAccountCommandHandler: CreateCheckingAccountCommandHandler,
    private val depositCommandHandler: DepositCommandHandler
) {

    fun create(ctx: Context) {
        println("Incoming request to create a new checking account")
        val request = ctx.body<CreateCheckingAccountRequest>()
        println("Request body: $request")
        val cmd = CreateCheckingAccountCommand(
            request.taxId,
            request.name
        )
        try {
            val accountId = createCheckingAccountCommandHandler.handle(cmd)
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

    fun deposit(ctx: Context) {
        println("Incoming request to deposit")
        val accountId = ctx.pathParam("account-id")
        val request = ctx.body<DepositRequest>()
        println("Request body: $request")
        val cmd = DepositCommand(
            accountId,
            request.value
        )
        try {
            depositCommandHandler.handle(cmd)
            ctx.status(HttpStatus.ACCEPTED_202)
        } catch (e: Exception) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
            println(e.message)
        }
    }
}