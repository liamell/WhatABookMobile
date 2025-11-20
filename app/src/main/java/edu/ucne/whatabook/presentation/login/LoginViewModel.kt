package edu.ucne.whatabook.presentation.login


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.data.datastore.TokenDataStore
import edu.ucne.whatabook.domain.usecase.LoginUseCase
import edu.ucne.whatabook.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val tokenStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UserNameChanged -> _uiState.value = _uiState.value.copy(userName = event.value)
            is LoginEvent.PasswordChanged -> _uiState.value = _uiState.value.copy(password = event.value)
            LoginEvent.SubmitLogin -> submitLogin()
            LoginEvent.ShowRegister -> { /* navigate or show sheet */ }
        }
    }


    private fun submitLogin() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val user = loginUseCase(_uiState.value.userName, _uiState.value.password)
                if (user == null) {
                    _uiState.value = _uiState.value.copy(error = "Credenciales inválidas", isLoading = false)
                } else {
                    val token = "token_" + user.usuarioId + "_" + System.currentTimeMillis()
                    tokenStore.saveToken(token, user.usuarioId.toString())
                    _uiState.value = _uiState.value.copy(usuario = user, isLoading = false)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error login", e)
                _uiState.value = _uiState.value.copy(error = "Error de red", isLoading = false)
            }
        }
    }


    fun register(userName: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val created = registerUseCase(userName, password)
                _uiState.value = _uiState.value.copy(usuario = created, isLoading = false)
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error register", e)
                _uiState.value = _uiState.value.copy(error = "Error al registrar", isLoading = false)
            }
        }
    }
}