package de.frederikkohler.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

@Serializable
data class CurrencyExchangeRate(
    var id: Int = 0,
    val sourceCurrency: String,
    val targetCurrency: String,
    val exchangeRate: Double,
    val updated_at: String = ""
)

object CurrencyExchangeRates : Table() {
    val id = integer("id").autoIncrement()
    val sourceCurrency = varchar("source_currency", length = 3)
    val targetCurrency = varchar("target_currency", length = 3)
    val exchangeRate = double("exchange_rate")
    val updated_at = datetime("updated_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}