package com.checkingaccount.application.config

import com.natpryce.konfig.*

class EnvironmentConfig(
    configuration: Configuration = EnvironmentVariables()
) {
    val serverPort = configuration[SERVER_PORT]
    val mongoConnection = configuration[MONGO_CONNECTION]

    companion object {
        val SERVER_PORT by intType
        val MONGO_CONNECTION by stringType
    }
}