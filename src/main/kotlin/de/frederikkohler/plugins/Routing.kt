package de.frederikkohler.plugins

import de.frederikkohler.model.*
import de.frederikkohler.services.EnvManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get
import java.time.LocalDate

fun Application.configureRouting(
    envManager: EnvManager =get()
) {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    val dotenv = envManager.getEnv()



    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/api/get-data") {
            val exchangeRate = client.get("https://api.devises.zone/v1/quotes/CHF/EUR/json?quantity=1&key=${dotenv["EXCHANGE"]}").body<ExchangeRateResponse>()
            val weaterResponse = client.get("https://api.openweathermap.org/data/2.5/weather?lat=47.62152071874679&lon=8.219410541948205&appid=${dotenv["WEATHER"]}&units=metric&lang=de").body<WeatherResponse>()

            val holiday = client.get("https://get.api-feiertage.de?years=2024&states=bw").body<GermanHolidaysResponse>()
            val newList = holiday.feiertage.filter { holiday ->
                LocalDate.parse(holiday.date).isAfter(LocalDate.now())
            }
            //val route = client.get("https://api.openrouteservice.org/v2/directions/driving-car?api_key=${dotenv["ROUTE"]}&start=8.218200,47.623665&end=8.226309,47.599893").body<RouteResponse>()



            call.respond(newList)
        }
    }
}

