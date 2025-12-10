package edu.ucne.whatabook.presentation.usuario

data class RegisterUiState(
    val nombre: String = "",
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val confirmPassword: String = "",

    val nombreError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
)
