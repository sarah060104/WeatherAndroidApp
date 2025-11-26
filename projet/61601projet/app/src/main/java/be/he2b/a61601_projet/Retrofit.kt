package be.he2b.a61601_projet

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class Retrofit {

    val  baseUrl = "https://uapuapedcteichqtojgx.supabase.co"

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonConverter = MoshiConverterFactory.create(moshi)

    val retrofitBuidler : Retrofit.Builder =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(jsonConverter)
    val retrofit : Retrofit = retrofitBuiler.build()
    val myHttpClient = retrofit.create(MyRetrofitHttpClient::class.java)
}