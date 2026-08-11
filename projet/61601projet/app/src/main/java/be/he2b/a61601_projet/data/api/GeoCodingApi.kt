package be.he2b.a61601_projet.data.api

import be.he2b.a61601_projet.data.requests.GeoCodingRequestBody
import be.he2b.a61601_projet.data.response.GeoCodingResponse
import be.he2b.a61601_projet.data.response.geoCodingResponse.MeteoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {

    @GET("search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 5,
        @Query("language") language: String = "fr",
        @Query("format") format: String = "json"
    ): GeoCodingResponse

}