package be.he2b.a61601_projet.ui.connect

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.he2b.a61601_projet.data.client.AuthClient
import be.he2b.a61601_projet.data.requests.AuthRequestBody
import be.he2b.a61601_projet.data.session.SessionStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception


class ConnectViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ConnectUiState())
    val uiState: StateFlow<ConnectUiState> = _uiState.asStateFlow()

    private val httpClient = AuthClient.myHttpClient

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
        val email = _uiState.value.email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update {
                it.copy(
                    isError = true,
                    errorMessage = "Adresse e-mail invalide"
                )
            }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isError = false, errorMessage = null) }
            try {
                val requestBody = AuthRequestBody(
                    email = email,
                    password = _uiState.value.password
                )
                val response = httpClient.signIn(requestBody)
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse != null) {
                        SessionStore.save(
                            accessToken = authResponse.accessToken,
                            refreshToken = authResponse.refreshToken,
                            userId = authResponse.user.id
                        )
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
                        errorMessage = e.message ?: "Erreur inconnue",
                        )
                }
            }
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigate = false) }
    }
}