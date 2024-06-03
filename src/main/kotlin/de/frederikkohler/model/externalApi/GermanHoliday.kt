package de.frederikkohler.model.externalApi

import kotlinx.serialization.Serializable

@Serializable
data class GermanHolidaysResponse(
    val status: String,
    val feiertage: List<GermanHoliday>
)

@Serializable
data class GermanHoliday(
    val date: String,
    val fname: String,
    val all_states: String,
    val bw: String,
    val by: String,
    val be: String,
    val bb: String,
    val hb: String,
    val hh: String,
    val he: String,
    val mv: String,
    val ni: String,
    val nw: String,
    val rp: String,
    val sl: String,
    val sn: String,
    val st: String,
    val sh: String,
    val th: String,
    val comment: String?,
    val augsburg: String?,
    val katholisch: String?
)