package be.he2b.a61601_projet.ui.home

import be.he2b.a61601_projet.data.database.Location
import be.he2b.a61601_projet.data.response.geoCodingResponse.MeteoResponse

data class HomeUiState(
    val favoriteCity: Location? = null,
    val weather: MeteoResponse? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
