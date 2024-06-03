package de.frederikkohler.model.externalApi

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateResponse(
    val result: Result,
    val status: String
)

@Serializable
data class Result(
    val updated: String,
    val source: String,
    val target: String,
    val value: Double,
    val quantity: Double,
    val amount: Double
)

