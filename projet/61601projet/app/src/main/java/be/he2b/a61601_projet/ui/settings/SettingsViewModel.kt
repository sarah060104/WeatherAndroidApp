package be.he2b.a61601_projet.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.data.client.AuthClient
import be.he2b.a61601_projet.data.model.Repository
import be.he2b.a61601_projet.data.session.SessionStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            Repository.observeAllLocations().collect { list ->
                _uiState.update {
                    it.copy(
                        locations = list,
                        favoriteId = list.firstOrNull { loc -> loc.isFavorite }?.id
                    )
                }
            }
        }
    }

    fun onCityMarkedFavorite(id: Long) {
        viewModelScope.launch {
            Repository.setFavorite(id)
        }
    }

    fun onSignOut() {
        if (_uiState.value.isSigningOut) return
        viewModelScope.launch {
            _uiState.update { it.copy(isSigningOut = true, errorMessage = null) }
            try {
                val token = SessionStore.accessToken.first()
                if (token != null) {
                    AuthClient.myHttpClient.signOut("Bearer $token")
                }
            } catch (_: Exception) {
            }
            SessionStore.clear()
            _uiState.update { it.copy(isSigningOut = false, signedOut = true) }
        }
    }

    fun onSignedOutHandled() {
        _uiState.update { it.copy(signedOut = false) }
    }
}
