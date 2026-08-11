package be.he2b.a61601_projet.data.response.geoCodingResponse

import com.squareup.moshi.Json

data class Hourly (
    @Json(name = "time")
    val time : List<String>,
    @Json(name = "temperature_2m")
    val temperature_2m : List<Double>,
    @Json(name = "precipitation_probability")
    val precipitation_probability : List<Int>,
    @Json(name = "weather_code")
    val weather_code : List<Int>
)
