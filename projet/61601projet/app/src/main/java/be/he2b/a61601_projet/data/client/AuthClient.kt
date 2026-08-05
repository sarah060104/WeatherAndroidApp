package be.he2b.a61601_projet.data.client

import be.he2b.a61601_projet.data.api.AuthApi

object AuthClient {



    private val  baseUrl = "https://uapuapedcteichqtojgx.supabase.co/"

    private val retrofit : retrofit2.Retrofit = RetrofitBuilder.createRetrofit(baseUrl)

    val myHttpClient: AuthApi by lazy{
        retrofit.create(AuthApi::class.java)}



}