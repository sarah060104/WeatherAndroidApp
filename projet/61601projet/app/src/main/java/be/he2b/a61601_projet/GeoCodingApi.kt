package be.he2b.a61601_projet

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {

    @GET("search?name=&count=1&language=fr&format=json")
    suspend fun searchCity(@Body requestBody: GeoCodingRequestBody): GeoCodingResponse
}

