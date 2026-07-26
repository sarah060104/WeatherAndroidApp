package be.he2b.a61601_projet.data.requests

import com.squareup.moshi.Json

data class AuthRequestBody(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)