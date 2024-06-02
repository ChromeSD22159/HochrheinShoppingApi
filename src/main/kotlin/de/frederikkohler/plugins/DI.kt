package de.frederikkohler.plugins

import de.frederikkohler.services.ENV
import de.frederikkohler.services.EnvManager
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import java.time.LocalDateTime

fun Application.configureDI(dotenv: ENV){
    val appModule= module {
        single<EnvManager> { EnvManager(dotenv) }
    }

    install(Koin){
        modules(appModule)
    }
}

