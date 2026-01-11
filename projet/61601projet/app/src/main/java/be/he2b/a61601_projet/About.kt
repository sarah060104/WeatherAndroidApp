package be.he2b.a61601_projet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.httpRequestEtCie.requests.ConnectionRequestBody
import be.he2b.a61601_projet.httpRequestEtCie.client.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    AboutScreen()
}


@Composable
fun AboutScreen(){
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sarah Okitombahe-tsha",
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Matricule : 61601")
            Spacer(modifier = Modifier.height(12.dp))
            Text("Groupe : E122")
        }
    }

}

class ConnectViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ConnectUiState())
    val uiState: StateFlow<ConnectUiState> = _uiState.asStateFlow()

    private val httpClient = Retrofit.myHttpClient

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it -> it.copy(email = newEmail)
        }
    }

    fun onPasswordChange(newPass : String){
        _uiState.update {
            it.copy(
                password = newPass,
            )
        }
    }

    fun onSubmit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isError = false) }
            try {
                val requestBody = ConnectionRequestBody(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                val response = httpClient.signIn(requestBody)
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null) {
                        _uiState.update { it.copy( navigate = true) }
                    } else {
                        _uiState.update {
                            it.copy(
                                isError = true,
                                errorMessage = "Réponse inattendue du serveur."
                            )
                        }
                    }
                }else{
                    _uiState.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Email ou mot de passe incorrect."
                        )
                    }
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Email ou mot de passe incorrect." // ou e.message
                    )
                }
            }
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigate = false) }
    }
}