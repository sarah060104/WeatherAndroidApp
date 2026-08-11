package be.he2b.a61601_projet.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.data.client.GeoCodingClient
import be.he2b.a61601_projet.data.model.Repository
import be.he2b.a61601_projet.data.response.savedCity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val httpClient = GeoCodingClient.myHttpClient

    /**
     * Watch the query field and trigger a search 300 ms after the user
     * stops typing. distinctUntilChanged avoids re-firing when the same
     * value is emitted (e.g. when other state fields change).
     */
    init {
        viewModelScope.launch {
            _uiState
                .map { it.cityToSearch }
                .distinctUntilChanged()
                .debounce(300)
                .collect { query ->
                    if (query.length >= 2) {
                        performSearch(query)
                    } else {
                        _uiState.update {
                            it.copy(searchResults = emptyList(), errorMessage = null)
                        }
                    }
                }
        }
    }

    fun onCityChange(newCity: String) {
        _uiState.update { it.copy(cityToSearch = newCity) }
    }

    /**
     * Kept for the keyboard "Search" IME action: hitting enter forces
     * an immediate search without waiting for the debounce.
     */
    fun onSearch() {
        val query = _uiState.value.cityToSearch
        if (query.isBlank()) return
        viewModelScope.launch { performSearch(query) }
    }

    private suspend fun performSearch(query: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val response = httpClient.searchCity(
                name = query,
                count = 5,
                language = "fr"
            )

            if (!response.result.isNullOrEmpty()) {
                val results = response.result.map {
                    savedCity(
                        id = it.id,
                        name = it.name,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        country = it.country,
                        admin1 = it.admin1
                    )
                }
                _uiState.update { it.copy(searchResults = results, isLoading = false) }
            } else {
                _uiState.update {
                    it.copy(
                        searchResults = emptyList(),
                        errorMessage = "Aucune ville trouvée",
                        isLoading = false
                    )
                }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
        }
    }

    fun onCityClicked(city: savedCity) {
        viewModelScope.launch {
            try {
                Repository.saveAndFavorite(city)
                _uiState.update { it.copy(navigateToHome = true, errorMessage = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    /**
     * Called by the Composable after it has consumed the navigateToHome flag
     * so the same event is not replayed on recomposition / back-navigation.
     */
    fun onNavigationHandled() {
        _uiState.update { it.copy(navigateToHome = false) }
    }
}
