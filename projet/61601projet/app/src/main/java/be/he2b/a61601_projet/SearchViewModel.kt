package be.he2b.a61601_projet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import be.he2b.a61601_projet.httpRequestEtCie.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val httpClient = GeoCoding.myHttpClient

    fun onCityChange(newCity: String) {
        _uiState.update {
           it.copy(cityToSearch = newCity)
        }
    }

    fun onSearch() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val longitude : Double
            val latitude : Double
            try {
                val requestbody = GeoCodingRequestBody(
                    name = _uiState.value.cityToSearch,
                    count = 1,
                    language = "fr",
                    format = "json"
                )
                val response = httpClient.searchCity(requestbody)
                longitude = response.result.get(0).longitude
                latitude = response.result.get(0).latitude

            }catch (e: Exception){
                _uiState.update { it.copy(errorMessage = e.message) }

            }
            try {
                val requestBody = MeteoRequestBody(
                    longitude = longitude,
                    latitude = latitude
                )
                val response = MeteoClient.myHttpClient.getMeteo(requestBody)

            } catch (e:Exception){
                _uiState.update { it.copy(errorMessage = e.message) }

            }
        }

    }



}