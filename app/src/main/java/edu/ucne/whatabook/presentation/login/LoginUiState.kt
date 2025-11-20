package edu.ucne.whatabook.presentation.login
import edu.ucne.whatabook.domain.model.Usuario


data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val usuario: Usuario? = null
)