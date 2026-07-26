package be.he2b.a61601_projet.ui.connect

data class ConnectUiState (
    val email:String = "",
    val domain:String ="he2b.be",
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val navigate: Boolean = false,
    val password: String=""
)