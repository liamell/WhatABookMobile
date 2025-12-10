package edu.ucne.whatabook.domain.usecase.user

import edu.ucne.whatabook.domain.model.Usuario
import edu.ucne.whatabook.domain.repository.UsuarioRepository

class RegisterUserUseCase(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(usuario: Usuario) {
        repository.register(usuario)
    }
}