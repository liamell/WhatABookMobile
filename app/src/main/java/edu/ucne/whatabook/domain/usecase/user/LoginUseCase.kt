package edu.ucne.whatabook.domain.usecase.user
import edu.ucne.whatabook.domain.repository.UsuarioRepository

class LoginUseCase(
    private val repository: UsuarioRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.login(email, password)
}