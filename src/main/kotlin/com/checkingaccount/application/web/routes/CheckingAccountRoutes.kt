package com.checkingaccount.application.web.routes

import com.checkingaccount.application.web.controllers.CheckingAccountController
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post

class CheckingAccountRoutes(
    private val checkingAccountController: CheckingAccountController
) {
    fun listen() {
        path("account") {
            post(checkingAccountController::create)
            path(":account-id") {
                path("deposit") {
                    post(checkingAccountController::deposit)
                }
                path("withdrawal") {
                    post(checkingAccountController::withdraw)
                }
            }
        }
    }
}