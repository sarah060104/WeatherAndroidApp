package be.he2b.a61601_projet.httpRequestEtCie.response.authResponse

import be.he2b.a61601_projet.httpRequestEtCie.response.User
import com.squareup.moshi.Json

data class AuthResponse(

    @Json(name = "access_token") val accessToken: String,
    @Json(name = "refresh_token") val refreshToken: String,
    @Json(name = "user") val user: User
)