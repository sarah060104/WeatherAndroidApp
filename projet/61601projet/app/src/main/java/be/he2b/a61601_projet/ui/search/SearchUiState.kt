package be.he2b.a61601_projet.ui.search

import be.he2b.a61601_projet.data.response.savedCities

data class SearchUiState(

    /**
     * data class with variables that are shown on the
     * search composable
     */
    val cityToSearch: String = "",
    val recentCities: List<savedCities> = emptyList(),//ville aec résultat
    val searchResults: List<savedCities> = emptyList(), //ville cherché
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)


