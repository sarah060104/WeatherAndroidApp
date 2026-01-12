package be.he2b.a61601_projet.httpRequestEtCie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.ConnectUiState
import be.he2b.a61601_projet.httpRequestEtCie.client.Retrofit
import be.he2b.a61601_projet.httpRequestEtCie.requests.ConnectionRequestBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

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