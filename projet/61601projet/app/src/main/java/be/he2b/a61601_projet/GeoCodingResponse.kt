package be.he2b.a61601_projet

import com.squareup.moshi.Json

class GeoCodingResponse {

    @Json(name = "result")
    val result : List<GeoCodingResult> = emptyList()
}