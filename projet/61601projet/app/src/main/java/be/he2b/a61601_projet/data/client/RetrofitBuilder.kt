package be.he2b.a61601_projet.data.client

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit

object RetrofitBuilder {

    /**
     * library that transform json object into kotlin object
     */
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    /**
     * converter configured with moshi library
     */
    private val jsonConverter = MoshiConverterFactory.create(moshi)

    /**
     * method used to configure retrofit/http request maker with the base url and the optional headers
     *
     */
    fun createRetrofit(baseUrl: String, headers: Map<String, String> = emptyMap()): Retrofit {
        val clientBuilder = OkHttpClient.Builder()

        if (headers.isNotEmpty()) {
            clientBuilder.addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder().apply {
                    headers.forEach { (key, value) ->
                        addHeader(key, value)
                    }
                }.build()
                chain.proceed(newRequest)
            }
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientBuilder.build())
            .addConverterFactory(jsonConverter)
            .build()
    }
}
