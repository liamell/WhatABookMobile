package edu.ucne.whatabook.domain.usecase.libros

import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.repository.LibroRepository
import javax.inject.Inject

class GuardarLibroUseCase @Inject constructor(
    private val repository: LibroRepository
) {
    suspend operator fun invoke(libro: Libro) {
        repository.insertRemoto(libro)
    }
}