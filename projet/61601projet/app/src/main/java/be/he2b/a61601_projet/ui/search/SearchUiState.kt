package be.he2b.a61601_projet.ui.search

import be.he2b.a61601_projet.data.response.savedCity

data class SearchUiState(

    /**
     * data class with variables that are shown on the
     * search composable
     */
    val cityToSearch: String = "",
    val recentCities: List<savedCity> = emptyList(),//ville aec résultat
    val searchResults: List<savedCity> = emptyList(), //ville cherché
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val navigateToHome: Boolean = false
)
