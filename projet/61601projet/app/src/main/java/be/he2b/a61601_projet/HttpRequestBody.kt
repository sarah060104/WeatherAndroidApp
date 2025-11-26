package be.he2b.a61601_projet

import com.squareup.moshi.Json

data class HttpRequestBody(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)