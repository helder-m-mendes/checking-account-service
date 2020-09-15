package com.checkingaccount.application.web.controllers

import com.checkingaccount.application.commands.CreateCheckingAccountCommandHandler
import com.checkingaccount.application.commands.DepositCommandHandler
import com.checkingaccount.application.commands.TransferCommandHandler
import com.checkingaccount.application.commands.WithdrawalCommandHandler
import com.checkingaccount.application.commands.dtos.CreateCheckingAccountCommand
import com.checkingaccount.application.commands.dtos.DepositCommand
import com.checkingaccount.application.commands.dtos.TransferCommand
import com.checkingaccount.application.commands.dtos.WithdrawalCommand
import com.checkingaccount.application.exceptions.AccountDoesNotExistException
import com.checkingaccount.application.exceptions.DuplicateAccountException
import com.checkingaccount.application.exceptions.InsufficientFundsException
import com.checkingaccount.application.exceptions.InvalidDataException
import com.checkingaccount.application.web.dtos.CreateCheckingAccountRequest
import com.checkingaccount.application.web.dtos.DepositRequest
import com.checkingaccount.application.web.dtos.TransferRequest
import com.checkingaccount.application.web.dtos.WithdrawalRequest
import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus

class CheckingAccountController(
    private val createCheckingAccountCommandHandler: CreateCheckingAccountCommandHandler,
    private val depositCommandHandler: DepositCommandHandler,
    private val withdrawalCommandHandler: WithdrawalCommandHandler,
    private val transferCommandHandler: TransferCommandHandler
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
        } catch (e: AccountDoesNotExistException) {
            ctx.status(HttpStatus.NOT_FOUND_404)
            e.message?.let {
                ctx.json(it)
            }
        }
    }

    fun withdraw(ctx: Context) {
        println("Incoming request to withdraw")
        val accountId = ctx.pathParam("account-id")
        val request = ctx.body<WithdrawalRequest>()
        println("Request body: $request")
        val cmd = WithdrawalCommand(
            accountId,
            request.value
        )
        try {
            val balance = withdrawalCommandHandler.handle(cmd)
            ctx.status(HttpStatus.ACCEPTED_202)
            ctx.json(balance)
        } catch (e: InsufficientFundsException) {
            ctx.status(HttpStatus.UNPROCESSABLE_ENTITY_422)
            e.message?.let {
                ctx.json(it)
            }
        } catch (e: AccountDoesNotExistException) {
            ctx.status(HttpStatus.NOT_FOUND_404)
            e.message?.let {
                ctx.json(it)
            }
        }
    }

    fun transfer(ctx: Context) {
        println("Incoming request to transfer")
        val accountId = ctx.pathParam("account-id")
        val request = ctx.body<TransferRequest>()
        println("Request body: $request")
        try {
            val cmd = TransferCommand(
                accountId,
                request.accountId,
                request.value
            )
            val balance = transferCommandHandler.handle(cmd)
            ctx.status(HttpStatus.ACCEPTED_202)
            ctx.json(balance)
        } catch (e: InsufficientFundsException) {
            ctx.status(HttpStatus.UNPROCESSABLE_ENTITY_422)
            e.message?.let {
                ctx.json(it)
            }
        }  catch (e: AccountDoesNotExistException) {
            ctx.status(HttpStatus.NOT_FOUND_404)
            e.message?.let {
                ctx.json(it)
            }
        } catch (e: InvalidDataException) {
            ctx.status(HttpStatus.BAD_REQUEST_400)
            e.message?.let {
                ctx.json(it)
            }
        }
    }
}