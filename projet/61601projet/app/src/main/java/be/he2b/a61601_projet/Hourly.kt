package be.he2b.a61601_projet

import com.squareup.moshi.Json
import java.time.LocalTime

data class Hourly (
    @Json(name = "time")
    val time : List<LocalTime> ,
    @Json(name = "temperature_2m")
    val temperature_2m : List<Double>,
    @Json(name = "precipitation_probability")
    val precipitation_probability : List<Int> ,
    @Json(name = "weathercode")
    val weather_code : List<Int> ,
    @Json(name = "temperature_m")
    val temperature_m : List<Double>


)