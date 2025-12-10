package edu.ucne.whatabook.domain.usecase.user

import edu.ucne.whatabook.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val userRepository: UsuarioRepository
) {

    operator fun invoke(): Flow<Int> {
        return userRepository.getCurrentUserId()
    }
}