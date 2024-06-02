package de.frederikkohler.mysql.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object ShoppingData : IntIdTable() {
    val timestamp = long("timestamp") // Zeitstempel des Einkaufs
    val weatherDescription = varchar("weather_description", length = 100) // Wetterbeschreibung
    val temperature = double("temperature") // Temperatur
    val humidity = integer("humidity") // Luftfeuchtigkeit
    val windSpeed = double("wind_speed") // Windgeschwindigkeit
    val rain = double("rain") // Regenmenge
    val dayOfWeek = integer("day_of_week") // Wochentag (0 für Montag, 1 für Dienstag usw.)
    val duration = double("duration") // Dauer der Reise zum Geschäft
    val isHolidayThisWeek = bool("is_holiday_this_week").default(false)
}

object CurrencyExchangeRates : IntIdTable() {
    val sourceCurrency = varchar("source_currency", length = 3)
    val targetCurrency = varchar("target_currency", length = 3)
    val exchangeRate = double("exchange_rate")
    val timestamp = long("timestamp")
}