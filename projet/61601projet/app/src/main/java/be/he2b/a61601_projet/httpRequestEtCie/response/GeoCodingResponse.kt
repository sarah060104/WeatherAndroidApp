package be.he2b.a61601_projet.httpRequestEtCie.response

import be.he2b.a61601_projet.httpRequestEtCie.response.GeoCodingResult
import com.squareup.moshi.Json

class GeoCodingResponse {

    @Json(name = "result")
    val result : List<GeoCodingResult> = emptyList()
}