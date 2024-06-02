package de.frederikkohler.plugins

import de.frederikkohler.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalDateTime

fun Application.configureRouting() {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/api/get-data") {
            val exchangeRate = client.get("https://api.devises.zone/v1/quotes/CHF/EUR/json?quantity=1&key=52639|K7UtqMvZCgdxjtPn3S2Z").body<ExchangeRateResponse>()
            val weaterResponse = client.get("https://api.openweathermap.org/data/2.5/weather?lat=47.62152071874679&lon=8.219410541948205&appid=16f61e39491ad9ed58aa3cedc12ad8d5&units=metric&lang=de").body<WeatherResponse>()

            val holiday = client.get("https://get.api-feiertage.de?years=2024&states=bw").body<GermanHolidaysResponse>()
            val newList = holiday.feiertage.filter { holiday ->
                LocalDate.parse(holiday.date).isAfter(LocalDate.now())
            }
            //val route = client.get("https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248ad5c2c1040be41ba87f36076bcfa83c5&start=8.218200,47.623665&end=8.226309,47.599893").body<RouteResponse>()



            call.respond(newList)
        }
    }
}

