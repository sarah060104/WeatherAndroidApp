package be.he2b.a61601_projet
import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ConnectViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ConnectUiState())
    val uiState: StateFlow<ConnectUiState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(
                email = newEmail,
                isError = false,
                errorMessage = "",
                navigate = false
            )
        }
    }

    fun onPasswordChange(newPass : String){
        _uiState.update {
            it.copy(

            )
        }
    }

    fun onSubmit() {
        val state = _uiState.value
        val email = state.email.trim()
        val formatOk = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val domainOk = email.lowercase().endsWith("@${state.domain}")

        if (!formatOk) {
            _uiState.update {
                it.copy(
                    isError = true,
                    errorMessage = "Adresse e-mail invalide",
                    navigate = false
                )
            }
            return
        }
        if (!domainOk) {
            _uiState.update {
                it.copy(
                    isError = true,
                    errorMessage = "L'adresse doit se terminer par @${state.domain}",
                    navigate = false
                )
            }
            return
        }

        _uiState.update { it.copy(isError = false, errorMessage = null, navigate = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigate = false) }
    }
}