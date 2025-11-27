package edu.ucne.whatabook.domain.usecase.libros

import edu.ucne.whatabook.domain.repository.LibroRepository
import javax.inject.Inject

class GetLibroByIdUseCase @Inject constructor(
    private val repository: LibroRepository
) {
    suspend operator fun invoke(id: Int) = repository.getLibroById(id)
}