package be.he2b.a61601_projet

import java.time.LocalTime

data class Daily(
    val time : List<String> = emptyList<String>(),
    val temperature_2m_max : List<Double> = emptyList<Double>(),
    val temperature_2m_min : List<Double> = emptyList<Double>(),
    val weather_code : List<Int> = emptyList<Int>(),


    )
