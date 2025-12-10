package edu.ucne.whatabook.presentation.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {


    val userSessionId: StateFlow<Int> =
        usuarioRepository.getUserSessionId().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    val userSessionName: StateFlow<String?> =
        usuarioRepository.getUserSessionName().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    val userSessionEmail: StateFlow<String?> =
        usuarioRepository.getUserSessionEmail().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    fun logout() {
        viewModelScope.launch {
            usuarioRepository.logout()
        }
    }
}