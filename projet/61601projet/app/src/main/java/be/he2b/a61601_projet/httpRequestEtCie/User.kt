package be.he2b.a61601_projet.httpRequestEtCie

import com.squareup.moshi.Json

data class User(
    @Json(name = "id") val id: String,
    @Json(name = "email") val email: String
)
