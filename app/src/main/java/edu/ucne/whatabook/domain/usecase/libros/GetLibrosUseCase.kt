package edu.ucne.whatabook.domain.usecase.libros

import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.repository.LibroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLibrosUseCase @Inject constructor(
    private val repository: LibroRepository
) {
    operator fun invoke(): Flow<List<Libro>> {
        return repository.getLibros()
    }
}