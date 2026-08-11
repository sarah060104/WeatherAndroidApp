package be.he2b.a61601_projet.data.response

import com.squareup.moshi.Json

data class savedCity(

    /**
     * data class used to save cities and their geo coding
     */
    @Json(name = "id")
    val id: Long,
    val name : String,
    val latitude: Double,
    val longitude: Double
)