package edu.ucne.whatabook.presentation.usuario
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.domain.usecase.user.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun login() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                loading = true,
                error = null,
                success = false
            )

            val state = _uiState.value
            val result = loginUseCase(state.email, state.password)

            if (result != null) {

                _uiState.value = _uiState.value.copy(
                    loading = false,
                    success = true
                )
            } else {

                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = "Credenciales incorrectas"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = _uiState.value.copy(
            error = null,
            success = false
        )
    }
}
