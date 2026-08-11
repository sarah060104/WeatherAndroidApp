package be.he2b.a61601_projet.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.he2b.a61601_projet.ui.search.formatCityLabel

@Composable
fun SettingsScreen(
    onLoggedOut: () -> Unit = {},
    viewModel: SettingsViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.signedOut) {
        if (state.signedOut) {
            onLoggedOut()
            viewModel.onSignedOutHandled()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Ville favorite",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Choisis la ville affichée sur l'écran d'accueil.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        if (state.locations.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Aucune ville sauvegardée.\nUtilise l'écran Recherche pour en ajouter.",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(state.locations, key = { it.id }) { location ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onCityMarkedFavorite(location.id) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = location.id == state.favoriteId,
                            onClick = { viewModel.onCityMarkedFavorite(location.id) }
                        )
                        Text(
                            text = formatCityLabel(location.name, location.admin1, location.country),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    HorizontalDivider()
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.onSignOut() },
            enabled = !state.isSigningOut,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.isSigningOut) "Déconnexion…" else "Se déconnecter")
        }
    }
}
