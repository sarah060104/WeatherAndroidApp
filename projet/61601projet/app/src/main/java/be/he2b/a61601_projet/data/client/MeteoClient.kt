package be.he2b.a61601_projet.data.client

import be.he2b.a61601_projet.data.api.MeteoApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object MeteoClient {

    private val baseUrl = "https://api.open-meteo.com/v1/forecast?"

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val Converter = MoshiConverterFactory.create(moshi)

    val retrofitBuidler : Retrofit.Builder =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(Converter)
    val retrofit : Retrofit = be.he2b.a61601_projet.data.client.Retrofit.retrofitBuidler.build()
    val myHttpClient: MeteoApi by lazy{
        retrofit.create(MeteoApi::class.java)}
}