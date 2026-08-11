package be.he2b.a61601_projet.data.client

import be.he2b.a61601_projet.data.api.MeteoApi

object MeteoClient {

    private val baseUrl = "https://api.open-meteo.com/"


    private val retrofit : retrofit2.Retrofit = RetrofitBuilder.createRetrofit(baseUrl)

    val myHttpClient: MeteoApi by lazy{ retrofit.create(MeteoApi::class.java)}


}