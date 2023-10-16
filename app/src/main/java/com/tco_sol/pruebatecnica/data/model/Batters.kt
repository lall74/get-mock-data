package com.tco_sol.pruebatecnica.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Batters(
    @Json(name = "batter") var batter: List<Batter>? = null,
)