package com.checkingaccount.application

import com.checkingaccount.application.web.routes.CheckingAccountRoutes
import io.javalin.Javalin
import org.koin.core.KoinComponent
import org.koin.core.inject

class CheckingAccountServiceApplication: KoinComponent
{
    private val checkingAccountRoutes: CheckingAccountRoutes by inject()

    fun init() {
        Javalin.create().apply {
            checkingAccountRoutes.listen()
        }.start(7000)
    }
}