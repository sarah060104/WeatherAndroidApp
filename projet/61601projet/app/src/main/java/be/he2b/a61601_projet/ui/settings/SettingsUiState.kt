package be.he2b.a61601_projet.ui.settings

import be.he2b.a61601_projet.data.database.Location

data class SettingsUiState(
    val locations: List<Location> = emptyList(),
    val favoriteId: Long? = null,
    val errorMessage: String? = null,
    val signedOut: Boolean = false,
    val isSigningOut: Boolean = false
)
