package be.he2b.a61601_projet.data.response

import com.squareup.moshi.Json

class GeoCodingResponse {

    @Json(name = "result")
    val result : List<savedCity> = emptyList()
}