package be.he2b.a61601_projet.httpRequestEtCie.client

import be.he2b.a61601_projet.httpRequestEtCie.GeoCodingApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GeoCoding {

    private val baseUrl = "https://geocoding-api.open-meteo.com/v1/search/";
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val Converter = MoshiConverterFactory.create(moshi)


    val retrofitBuidler : Retrofit.Builder =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(Converter)
    val retrofit : Retrofit = be.he2b.a61601_projet.httpRequestEtCie.client.Retrofit.retrofitBuidler.build()
    val myHttpClient: GeoCodingApi by lazy{
        retrofit.create(GeoCodingApi::class.java)}
}