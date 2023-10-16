package com.tco_sol.pruebatecnica.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Topping(
    @Json(name = "id") var id: String? = null,
    @Json(name = "type") var type: String? = null,
)