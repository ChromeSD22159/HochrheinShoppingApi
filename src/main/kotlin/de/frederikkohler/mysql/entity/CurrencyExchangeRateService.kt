package de.frederikkohler.mysql.entity

import de.frederikkohler.model.CurrencyExchangeRate
import de.frederikkohler.model.CurrencyExchangeRates
import de.frederikkohler.plugins.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

interface CurrencyExchangeRateService {
    suspend fun addCurrencyRate(currency: CurrencyExchangeRate): CurrencyExchangeRate?
    suspend fun listAllCurrencyRates(): List<CurrencyExchangeRate>
}

class CurrencyExchangeRatesDataDataserviceService: CurrencyExchangeRateService {
    private fun resultRowCurrencyExchangeRate(row: ResultRow): CurrencyExchangeRate {
        return CurrencyExchangeRate(
            id = row[CurrencyExchangeRates.id],
            sourceCurrency = row[CurrencyExchangeRates.sourceCurrency],
            targetCurrency = row[CurrencyExchangeRates.targetCurrency],
            exchangeRate = row[CurrencyExchangeRates.exchangeRate],
            updated_at = row[CurrencyExchangeRates.updated_at].toString()
        )
    }

    override suspend fun addCurrencyRate(currency: CurrencyExchangeRate): CurrencyExchangeRate? = dbQuery {
        val zonedDateTime = ZonedDateTime.parse(currency.updated_at, DateTimeFormatter.ISO_DATE_TIME)
        val localDateTime = zonedDateTime.toLocalDateTime()
        val insertStmt = CurrencyExchangeRates.insert {
            it[sourceCurrency] = currency.sourceCurrency
            it[targetCurrency] = currency.targetCurrency
            it[exchangeRate] = currency.exchangeRate
            it[updated_at] = localDateTime
        }
        insertStmt.resultedValues?.singleOrNull()?.let { resultRowCurrencyExchangeRate(it) }
    }

    override suspend fun listAllCurrencyRates(): List<CurrencyExchangeRate> = dbQuery {
        CurrencyExchangeRates
            .selectAll()
            .map { resultRowCurrencyExchangeRate(it) }
    }
}