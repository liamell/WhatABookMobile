package edu.ucne.whatabook.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.domain.model.Usuario
import edu.ucne.whatabook.domain.usecase.user.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onNombreChange(value: String) {
        _uiState.value = _uiState.value.copy(
            nombre = value,
            nombreError = if (value.isBlank()) "El nombre no puede estar vacío" else null
        )
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(
            email = value,
            emailError = when {
                value.isBlank() -> "El correo no puede estar vacío"
                else -> null
            }
        )
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(
            password = value,
            passwordError = when {
                value.isBlank() -> "La contraseña no puede estar vacía"
                value.length < 6 -> "Debe tener mínimo 6 caracteres"
                else -> null
            }

        )
    }


    private fun isValid(): Boolean {
        val state = _uiState.value
        return state.nombreError == null &&
                state.emailError == null &&
                state.passwordError == null &&
                state.confirmPasswordError == null &&
                state.nombre.isNotBlank() &&
                state.email.isNotBlank() &&
                state.password.isNotBlank()

    }

    fun register() {
        if (!isValid()) {
            _uiState.value = _uiState.value.copy(error = "Corrige los campos marcados en rojo")
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true, error = null)

                val state = _uiState.value

                registerUserUseCase(
                    Usuario(
                        nombre = state.nombre,
                        correo = state.email,
                        password = state.password
                    )
                )

                _uiState.value = _uiState.value.copy(
                    success = true,
                    loading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }
}
