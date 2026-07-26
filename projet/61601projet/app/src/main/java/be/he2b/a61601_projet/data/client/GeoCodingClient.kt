package be.he2b.a61601_projet.data.client

import be.he2b.a61601_projet.data.api.GeoCodingApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory

object GeoCodingClient {

    private val baseUrl = "https://geocoding-api.open-meteo.com/v1/search/";
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val Converter = MoshiConverterFactory.create(moshi)


    val retrofitBuidler : retrofit2.Retrofit.Builder =
        retrofit2.Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(Converter)
    val retrofit : retrofit2.Retrofit = Retrofit.retrofitBuidler.build()
    val myHttpClient: GeoCodingApi by lazy{
        retrofit.create(GeoCodingApi::class.java)}
}