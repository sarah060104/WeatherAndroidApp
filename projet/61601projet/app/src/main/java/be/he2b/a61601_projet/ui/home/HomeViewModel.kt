package be.he2b.a61601_projet.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.data.client.MeteoClient
import be.he2b.a61601_projet.data.database.Location
import be.he2b.a61601_projet.data.database.WeatherEntry
import be.he2b.a61601_projet.data.model.Repository
import be.he2b.a61601_projet.data.response.geoCodingResponse.DailyResponse
import be.he2b.a61601_projet.data.response.geoCodingResponse.Hourly
import be.he2b.a61601_projet.data.response.geoCodingResponse.MeteoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            Repository.observeFavorites()
                .map { it.firstOrNull() }
                .distinctUntilChanged()
                .collect { city ->
                    _uiState.update { it.copy(favoriteCity = city, errorMessage = null) }
                    if (city == null) {
                        _uiState.update { it.copy(weather = null, isLoading = false) }
                    } else {
                        loadWeather(city)
                    }
                }
        }
    }

    private suspend fun loadWeather(city: Location) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val response = if (Repository.isCacheFresh(city.id)) {
                val cached = Repository.observeWeatherForLocation(city.id).first()
                cachedEntriesToResponse(cached)
            } else {
                val fresh = MeteoClient.myHttpClient.getWeather(city.latitude, city.longitude)
                Repository.saveWeatherForLocation(city.id, responseToEntries(city.id, fresh))
                fresh
            }
            _uiState.update { it.copy(weather = response, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        }
    }

    /**
     * Convert the API's DailyResponse into cache rows for Room.
     * Dates arrive as "YYYY-MM-DD"; we store them as epoch millis (UTC midnight)
     * so `cleanupPastWeather` can compare against System.currentTimeMillis().
     */
    private fun responseToEntries(locationId: Long, response: MeteoResponse): List<WeatherEntry> {
        val daily = response.daily
        return daily.time.indices.map { i ->
            WeatherEntry(
                locationId = locationId,
                date = parseDateToMillis(daily.time[i]),
                tempMin = daily.temperature_2m_min[i],
                tempMax = daily.temperature_2m_max[i],
                weatherCode = daily.weather_code[i]
            )
        }
    }

    /**
     * Rebuild a MeteoResponse from cache rows. Hourly is left empty
     * because we only cache daily aggregates (enough for HomeScreen).
     */
    private fun cachedEntriesToResponse(entries: List<WeatherEntry>): MeteoResponse {
        val sorted = entries.sortedBy { it.date }
        return MeteoResponse(
            hourly = Hourly(
                time = emptyList(),
                temperature_2m = emptyList(),
                precipitation_probability = emptyList(),
                weather_code = emptyList()
            ),
            daily = DailyResponse(
                time = sorted.map { formatMillisToDate(it.date) },
                temperature_2m_max = sorted.map { it.tempMax },
                temperature_2m_min = sorted.map { it.tempMin },
                weather_code = sorted.map { it.weatherCode }
            )
        )
    }

    private fun parseDateToMillis(iso: String): Long =
        java.time.LocalDate.parse(iso)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

    private fun formatMillisToDate(millis: Long): String =
        DateTimeFormatter.ISO_LOCAL_DATE.format(
            Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate()
        )
}
