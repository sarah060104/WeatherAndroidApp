package be.he2b.a61601_projet.httpRequestEtCie

import be.he2b.a61601_projet.httpRequestEtCie.requests.GeoCodingRequestBody
import be.he2b.a61601_projet.httpRequestEtCie.response.GeoCodingResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface GeoCodingApi {

    @GET("search?name=&count=1&language=fr&format=json")
    suspend fun searchCity(@Body requestBody: GeoCodingRequestBody): GeoCodingResponse
}

