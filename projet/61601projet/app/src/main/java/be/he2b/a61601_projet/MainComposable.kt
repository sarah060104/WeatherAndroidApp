package be.he2b.a61601_projet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.he2b.a61601_projet.ui.theme._61601projetTheme


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
    Connecting(
        email = state.email,
        isError = state.isError,
        errorMessage = state.errorMessage,
        onEmailChange = viewModel::onEmailChange,
        onSubmit = viewModel::onSubmit,
        password = "",
        onPaChange = {}
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

@Preview(showBackground = true)
@Composable
fun ConnectingPreview(){
    _61601projetTheme {
        Connecting(
            email = "",
            errorMessage = "Adresse e-mail invalide",
            isError = false,
            onEmailChange = {},
            onSubmit = {},
            modifier = Modifier,
            password = "",
            onPaChange = {}
        )
    }
}