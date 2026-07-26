package be.he2b.a61601_projet.data.client

import be.he2b.a61601_projet.data.api.GeoCodingApi

object GeoCodingClient {

    private val baseUrl = "https://geocoding-api.open-meteo.com/v1/search/"

    private val retrofit : retrofit2.Retrofit = RetrofitBuilder.createRetrofit(baseUrl)

    val myHttpClient: GeoCodingApi by lazy{
        retrofit.create(GeoCodingApi::class.java)}

}