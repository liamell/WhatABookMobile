package edu.ucne.whatabook.domain.usecase.libros
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.repository.LibroRepository
import javax.inject.Inject

class SearchLibrosUseCase @Inject constructor(
    private val repository: LibroRepository
) {
    suspend operator fun invoke(query: String): List<Libro> {
        return repository.searchLibros(query)
    }
}
