package com.tco_sol.pruebatecnica.data.model

import com.squareup.moshi.Json

data class Topping(
    @Json(name = "id") var id: String? = null,
    @Json(name = "type") var type: String? = null,
)