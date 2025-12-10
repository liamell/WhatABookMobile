package edu.ucne.whatabook.domain.usecase.libros

import edu.ucne.whatabook.domain.repository.LibroRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import edu.ucne.whatabook.domain.model.Libro

class GetLibrosUseCase @Inject constructor(
    private val repository: LibroRepository
) {
    operator fun invoke(): Flow<List<Libro>> {
        return repository.getLibros()
    }
}