package be.he2b.a61601_projet.httpRequestEtCie

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Retrofit {

    /**
     * Class who takes care of the configuration of retrofit
     * TODO: classe retrofit principale qui gère la configuration de retrofit,
     * TODO: et autre classe herite de celle ci et gère les requetes
     *
     */

    private val  baseUrl = "https://uapuapedcteichqtojgx.supabase.co"

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val jsonConverter = MoshiConverterFactory.create(moshi)

    /*
        constructor of request + base url(self-explanatory) + converter json
     */
    val retrofitBuidler : Retrofit.Builder =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(jsonConverter)
    val retrofit : Retrofit = retrofitBuidler.build()
    val myHttpClient: MyRetrofitHttpClient by lazy{
        retrofit.create(MyRetrofitHttpClient::class.java)}

}