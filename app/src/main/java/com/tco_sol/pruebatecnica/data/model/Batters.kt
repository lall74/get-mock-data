package com.tco_sol.pruebatecnica.data.model

import com.squareup.moshi.Json

data class Batters(
    @Json(name = "batter") var batter: List<Batter>? = null,
)