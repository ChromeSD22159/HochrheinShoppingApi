package de.frederikkohler.plugins

import de.frederikkohler.model.CurrencyExchangeRates
import de.frederikkohler.services.DatabasesManager
import de.frederikkohler.services.EnvManager
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException
import org.koin.ktor.ext.get

fun Application.configureDatabases(
    envManager: EnvManager =get()
): Database {
    val db: Database = try {
        DatabasesManager(
            dotenv = envManager.getEnv()
        ).connection ?: throw SQLException("Datenbankverbindung ist null.")
    } catch (e: SQLException) {
        println("Connection failed: " + e.message)
        throw e
    }

    transaction(db){
        SchemaUtils.create(
            CurrencyExchangeRates
        )

        launch(Dispatchers.IO) {
            DatabasesManager(
                dotenv = envManager.getEnv()
            )
        }
    }

    return db
}

suspend fun <T> dbQuery(block:suspend ()->T):T{
    return newSuspendedTransaction(Dispatchers.IO) { block() }
}

