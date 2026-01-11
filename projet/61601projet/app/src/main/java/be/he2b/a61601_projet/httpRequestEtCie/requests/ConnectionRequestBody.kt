package be.he2b.a61601_projet.httpRequestEtCie.requests

import com.squareup.moshi.Json

data class ConnectionRequestBody(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)