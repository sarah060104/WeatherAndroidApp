package be.he2b.a61601_projet.data.requests

import com.squareup.moshi.Json

data class GeoCodingRequestBody (

    @Json
    val name:String,
    @Json
    val count: Int = 1,
    @Json
    val language: String,
    @Json
    val format: String = "json"

)