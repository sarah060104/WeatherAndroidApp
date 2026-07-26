package be.he2b.a61601_projet.data.response

import com.squareup.moshi.Json

data class User(
    /**
     * Data class used for the response of the api call for the authentification
     */
    @Json(name = "id") val id: String,
    @Json(name = "email") val email: String
)