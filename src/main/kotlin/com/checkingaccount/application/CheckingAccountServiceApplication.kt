package com.checkingaccount.application

import com.checkingaccount.application.config.EnvironmentConfig
import com.checkingaccount.application.config.Modules
import com.checkingaccount.application.web.routes.CheckingAccountRoutes
import io.javalin.Javalin
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class CheckingAccountServiceApplication: KoinComponent
{
    private val environmentConfig: EnvironmentConfig by inject()
    private val checkingAccountRoutes: CheckingAccountRoutes by inject()

    fun init() {
        startKoin {
           modules(Modules.modules())
        }
        Javalin.create().apply {
            routes {
                checkingAccountRoutes.listen()
            }
        }.start(environmentConfig.serverPort)
    }
}