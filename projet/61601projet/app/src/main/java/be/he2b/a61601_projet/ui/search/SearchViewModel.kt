package be.he2b.a61601_projet.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.data.client.GeoCodingClient
import be.he2b.a61601_projet.data.requests.GeoCodingRequestBody
import be.he2b.a61601_projet.data.response.savedCity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val httpClient = GeoCodingClient.myHttpClient

    /**
     * method used when a new city is entered in the search area
     */
    fun onCityChange(newCity: String) {
        _uiState.update {
           it.copy(cityToSearch = newCity)
        }
    }

    /**
     * Method call when the user click on the "ok"/enter button on keyword
     * first, it'll search for the city's longitude and latitude and register it in a
     * list of result for each city to get more recent weather info faster for them
     *
     * Then it'll search the infos of the weather for the city according to it's
     * longitude and latitude
     *
     *
     */


    fun onSearch() {
        if (_uiState.value.cityToSearch.isBlank()) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val response = httpClient.searchCity(
                    name = _uiState.value.cityToSearch,
                    count = 5,
                    language = "fr"
                )

                if (!response.result.isNullOrEmpty()) {
                    val results = response.result.map {
                        savedCity(
                            name = it.name,
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    }
                    _uiState.update {
                        it.copy(searchResults = results, isLoading = false)
                    }
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
                _uiState.update {
                    it.copy(errorMessage = e.message, isLoading = false)
                }
            }
        }
    }




}