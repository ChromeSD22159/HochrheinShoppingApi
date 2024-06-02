package de.frederikkohler.model

import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    val type: String,
    val bbox: List<Double>,
    val features: List<Feature>,
    val metadata: Metadata,
)

@Serializable
data class Feature(
    val bbox: List<Double>,
    val type: String,
    val properties: Properties,
    val geometry: Geometry,
)

@Serializable
data class Properties(
    val segments: List<Segment>,
    val way_points: List<Long>,
    val summary: Summary,
)

@Serializable
data class Segment(
    val distance: Double,
    val duration: Double,
    val steps: List<Step>,
)

@Serializable
data class Step(
    val distance: Double,
    val duration: Double,
    val type: Long,
    val instruction: String,
    val name: String,
    val way_points: List<Long>,
    val exit_number: Long?,
)

@Serializable
data class Summary(
    val distance: Double,
    val duration: Double,
)

@Serializable
data class Geometry(
    val coordinates: List<List<Double>>,
    val type: String,
)

@Serializable
data class Metadata(
    val attribution: String,
    val service: String,
    val timestamp: Long,
    val query: Query,
    val engine: Engine,
)

@Serializable
data class Query(
    val coordinates: List<List<Double>>,
    val profile: String,
    val format: String,
)

@Serializable
data class Engine(
    val version: String,
    val build_date: String,
    val graph_date: String,
)
