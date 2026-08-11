package be.he2b.a61601_projet.data.response

import com.squareup.moshi.Json

data class GeoCodingResponse(
    @Json(name = "results")
    val result: List<savedCity> = emptyList()
)
