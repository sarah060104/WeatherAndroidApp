package be.he2b.a61601_projet.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.he2b.a61601_projet.data.response.savedCity
import be.he2b.a61601_projet.ui.theme._61601projetTheme


@Composable
fun Searching(
    viewModel: SearchViewModel = viewModel()
){
    val state = viewModel.uiState.collectAsState().value
    SearchScreen(
        searchItem = state.cityToSearch,
        onQueryChange = { newValue -> viewModel.onCityChange(newValue)},
        modifier = Modifier,
        e = state.searchResults.size.toString(),
        onSearch = { viewModel.onSearch()},
        results = state.searchResults,
        isLoading = state.isLoading
    )

}

@Preview(showBackground = true)
@Composable
fun SearchPreview(

){
    _61601projetTheme {
        SearchScreen(
            searchItem = "Bruxelles",
            onQueryChange = {},
            modifier = Modifier,
            e = "test",
            onSearch = {},
            results = listOf(savedCity("Bruxelles", 1.0, 2.0)),
            isLoading = false
        )
    }
}

@Composable
fun SearchScreen(
    searchItem : String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    e : String,
    onSearch: () -> Unit,
    results: List<savedCity>,
    isLoading: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(

                value = searchItem,
                onValueChange = onQueryChange,

                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp, 64.dp, 16.dp, 16.dp),
                placeholder = { Text("Search...") },
                leadingIcon = {
                    if (isLoading) {LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.1f)) // Or some other indicator
                    } else {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon")
                    }
                },

                trailingIcon = {
                    if (searchItem.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardActions = KeyboardActions(onSearch = { onSearch() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),

                )
            LazyColumn {
                items(results) {it ->
                    CitySearched(city = it.name, longitude = it.longitude, latitude = it.latitude)
                }
            }
        }
    }
}

@Composable
fun CitySearched(
    city: String,
    longitude: Double,
    latitude: Double) {
    Box(modifier = Modifier.padding(8.dp)) {
        Text(text = "$city ($latitude, $longitude)")
    }
}