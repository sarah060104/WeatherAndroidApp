package be.he2b.a61601_projet.data.api

import be.he2b.a61601_projet.data.response.geoCodingResponse.MeteoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MeteoApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("hourly") hourly: String = "weather_code,temperature_2m,relative_humidity_2m,precipitation_probability",
        @Query("timezone") timezone: String = "auto"
    ): MeteoResponse
}