package com.tco_sol.pruebatecnica.data.model

import com.squareup.moshi.Json

data class Product(
    @Json(name = "id") var id: String? = null,
    @Json(name = "type") var type: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "ppu") var ppu: Double? = null,
    @Json(name = "batters") var batters: Batters? = Batters(),
    @Json(name = "topping") var topping: List<Topping>? = null
)
