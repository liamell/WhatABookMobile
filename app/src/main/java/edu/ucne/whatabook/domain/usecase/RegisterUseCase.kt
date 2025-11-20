package edu.ucne.whatabook.domain.usecase

import edu.ucne.whatabook.domain.model.Usuario
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import javax.inject.Inject


class RegisterUseCase @Inject constructor(private val repo: UsuarioRepository) {
    suspend operator fun invoke(userName: String, password: String): Usuario = repo.register(userName, password)
}