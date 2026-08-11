package be.he2b.a61601_projet.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.data.model.Repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = Repository.observeAllLocations()
        .map { list ->
            SettingsUiState(
                locations = list,
                favoriteId = list.firstOrNull { it.isFavorite }?.id
            )
        }
        .catch { e -> emit(SettingsUiState(errorMessage = e.message)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SettingsUiState()
        )

    fun onCityMarkedFavorite(id: Long) {
        viewModelScope.launch {
            Repository.setFavorite(id)
        }
    }
}
