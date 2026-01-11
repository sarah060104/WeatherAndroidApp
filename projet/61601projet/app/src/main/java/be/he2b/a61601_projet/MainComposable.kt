package be.he2b.a61601_projet

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.he2b.a61601_projet.ConnectViewModel
import be.he2b.a61601_projet.ui.theme._61601projetTheme
import be.he2b.a61601_projet.SearchViewModel
import be.he2b.a61601_projet.R
import be.he2b.a61601_projet.httpRequestEtCie.response.GeoCodingResult


@Composable
fun HomeScreen() {
    Box(Modifier
        .fillMaxSize()
        .background(color = Color.White),
        contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "LogoESI"
        )
    }
}

@Composable
fun ConnectingRoute(
    onNavigateHome: () -> Unit,
    viewModel: ConnectViewModel = viewModel()
) {
    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(state.navigate) {
        if (state.navigate) {
            onNavigateHome()
            viewModel.onNavigated()
        }
    }
    ConnectingScreen(
        email = state.email,
        isError = state.isError,
        errorMessage = state.errorMessage,
        onEmailChange = viewModel::onEmailChange,
        onSubmit = viewModel::onSubmit,
        password = state.password,
        onPasswordChange = viewModel::onPasswordChange,
        modifier = Modifier
    )

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Connecting(
    email: String,
    isError: Boolean,
    errorMessage: String?,
    onEmailChange: (String) -> Unit,
    onPaChange:(String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    password: String) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Row (
                modifier.fillMaxWidth(3 / 4f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                OutlinedTextField(
                    modifier = modifier
                        .testTag("emailField"),
                    singleLine = true,
                    value = email,
                    onValueChange = onEmailChange,
                    isError = isError,
                    textStyle = LocalTextStyle.current.copy(color = colorResource(R.color.black)),
                    label = { Text(stringResource(R.string.email_input)) },
                    supportingText = {
                        if (isError) Text(errorMessage ?: stringResource(R.string.email_not_ok))
                    }
                )
            }
            Row (
                modifier.fillMaxWidth(3 / 4f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                OutlinedTextField(
                    modifier = modifier
                        .testTag("passwordField"),
                    value = password,
                    onValueChange = onPaChange,
                    isError = isError,
                    textStyle = LocalTextStyle.current.copy(color = colorResource(R.color.black)),
                    label =  {Text(stringResource(R.string.password))}
                )}


        }
        Row(
            modifier.fillMaxWidth(3 / 4f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { onSubmit()},
                modifier = modifier
                    .padding(5.dp)
                    .testTag("okButton")
            ) {
                Text(stringResource(R.string.ok))
            }
        }
        Row {
            Text(errorMessage.toString())
        }

    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectingScreen(
    email: String,
    password: String,
    isError: Boolean,
    errorMessage: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val childModifier = Modifier
                .fillMaxWidth(0.8f)

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text(stringResource(R.string.email_input)) },
                isError = isError,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                singleLine = true,
                modifier = childModifier.testTag("emailField")
            )

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(stringResource(R.string.password)) },
                isError = isError,
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onSubmit() }),
                modifier = childModifier.testTag("passwordField"),
                visualTransformation = PasswordVisualTransformation()


            )

            if (isError && errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = childModifier.padding(top = 4.dp)
                )
            }

            Button(
                onClick = onSubmit,
                modifier = childModifier
                    .padding(top = 16.dp)
                    .testTag("okButton")
            ) {
                Text(stringResource(R.string.ok))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ConnectingPreview(){
    _61601projetTheme {

        ConnectingScreen(
            email = "",
            password = "abc",
            errorMessage = "Adresse e-mail invalide",
            isError = false,
            onEmailChange = {},
            onPasswordChange = {},
            onSubmit = {},
            modifier = Modifier,
        )
    }
}


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
            results = listOf(GeoCodingResult("Bruxelles", 1.0, 2.0)),
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
    results: List<GeoCodingResult>,
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



