package be.he2b.a61601_projet.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.he2b.a61601_projet.data.database.Location
import be.he2b.a61601_projet.data.response.geoCodingResponse.MeteoResponse
import be.he2b.a61601_projet.ui.search.formatCityLabel

@Composable
fun HomeScreen(
    onNavigateToSearch: () -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    when {
        state.isLoading && state.weather == null -> LoadingContent()
        state.favoriteCity == null -> EmptyHomeContent(onNavigateToSearch = onNavigateToSearch)
        else -> WeatherContent(
            city = state.favoriteCity!!,
            weather = state.weather,
            errorMessage = state.errorMessage
        )
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyHomeContent(onNavigateToSearch: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enregistrez une ville favorite ou activez la géolocalisation",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onNavigateToSearch,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Chercher une ville")
        }
    }
}

@Composable
private fun WeatherContent(
    city: Location,
    weather: MeteoResponse?,
    errorMessage: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatCityLabel(city.name, city.admin1, city.country),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        if (errorMessage != null && weather == null) {
            Text(
                text = "Erreur : $errorMessage",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
            return
        }

        if (weather == null || weather.daily.time.isEmpty()) {
            CircularProgressIndicator()
            return
        }

        val daily = weather.daily
        TodayCard(
            code = daily.weather_code.first(),
            tempMin = daily.temperature_2m_min.first(),
            tempMax = daily.temperature_2m_max.first()
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Prochains jours",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // zip daily arrays: (date, max, min, code) starting from tomorrow (index 1)
        val upcoming = daily.time.drop(1).mapIndexed { i, date ->
            DayForecast(
                date = date,
                tempMax = daily.temperature_2m_max.getOrElse(i + 1) { 0.0 },
                tempMin = daily.temperature_2m_min.getOrElse(i + 1) { 0.0 },
                weatherCode = daily.weather_code.getOrElse(i + 1) { 0 }
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(upcoming) { day -> DayRow(day) }
        }
    }
}

@Composable
private fun TodayCard(code: Int, tempMin: Double, tempMax: Double) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = weatherEmoji(code), style = MaterialTheme.typography.displayLarge)
            Text(text = weatherLabel(code), style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                text = "min ${tempMin}° / max ${tempMax}°",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

private data class DayForecast(
    val date: String,
    val tempMin: Double,
    val tempMax: Double,
    val weatherCode: Int
)

@Composable
private fun DayRow(day: DayForecast) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = day.date.substringAfterLast("-"), style = MaterialTheme.typography.bodyMedium)
        Text(text = weatherEmoji(day.weatherCode), style = MaterialTheme.typography.titleLarge)
        Text(
            text = "${day.tempMin}° / ${day.tempMax}°",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/**
 * add emoji according to weather code
 * Open-Meteo WMO weather codes → emoji. Reference:
 * https://open-meteo.com/en/docs (weather_code section)
 */
private fun weatherEmoji(code: Int): String = when (code) {
    0 -> "☀️"
    1, 2 -> "🌤️"
    3 -> "☁️"
    45, 48 -> "🌫️"
    in 51..57 -> "🌦️"
    in 61..67 -> "🌧️"
    in 71..77 -> "🌨️"
    in 80..82 -> "🌧️"
    in 85..86 -> "🌨️"
    95, 96, 99 -> "⛈️"
    else -> "❓"
}

private fun weatherLabel(code: Int): String = when (code) {
    0 -> "Ensoleillé"
    1, 2 -> "Partiellement nuageux"
    3 -> "Couvert"
    45, 48 -> "Brouillard"
    in 51..57 -> "Bruine"
    in 61..67 -> "Pluie"
    in 71..77 -> "Neige"
    in 80..82 -> "Averses"
    in 85..86 -> "Averses de neige"
    95, 96, 99 -> "Orage"
    else -> "—"
}
