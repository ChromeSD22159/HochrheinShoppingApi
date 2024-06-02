package de.frederikkohler

import de.frederikkohler.plugins.*
import de.frederikkohler.services.ENV
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val env = ENV.Development

    configureDI(env)
    configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureRouting()
}
