package be.he2b.a61601_projet

data class SearchUiState(
    val cityToSearch: String = "",
    val recentCities: List<GeoCodingResult> = emptyList() ,//ville aec résultat
    val searchResults: List<GeoCodingResult> = emptyList(), //ville cherché
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
