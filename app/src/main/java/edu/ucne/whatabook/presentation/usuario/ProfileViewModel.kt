package edu.ucne.whatabook.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.data.local.datastore.SessionManager
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val nombre: String = "",
    val email: String = "",
    val passwordMasked: String = "••••••••",
    val profileImageUri: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        observeSession()
    }

    private fun observeSession() {

        viewModelScope.launch {
            sessionManager.userId.collect { idOrNull ->
                if (idOrNull != null && idOrNull != 0) {

                    val usuario = usuarioRepository.getUsuarioById(idOrNull)
                    usuario?.let {
                        _uiState.value = _uiState.value.copy(
                            nombre = it.nombre,
                            email = it.correo,
                            profileImageUri = it.fotoPerfilUri
                        )
                    }
                } else {

                    _uiState.value = ProfileUiState()
                }
            }
        }
    }


    fun logout(onComplete: (() -> Unit)? = null) {
        viewModelScope.launch {
            sessionManager.logout()
            _uiState.value = ProfileUiState()
            onComplete?.invoke()
        }
    }

    fun updateProfileImage(uri: String) {
        viewModelScope.launch {
            val id = sessionManager.userId.first()

            if (id != null && id != 0) {
                usuarioRepository.updateFotoPerfil(id, uri)
                _uiState.value = _uiState.value.copy(profileImageUri = uri)
            }
        }
    }



}