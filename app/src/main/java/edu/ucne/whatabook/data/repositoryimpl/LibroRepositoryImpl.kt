package edu.ucne.whatabook.data.repositoryimpl

import edu.ucne.whatabook.data.local.dao.LibroDao
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.repository.LibroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LibroRepositoryImpl(
    private val libroDao: LibroDao
) : LibroRepository {

    override fun getLibros(): Flow<List<Libro>> {
        return libroDao.getLibros().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getLibroById(id: Int): Libro? {
        return libroDao.getLibroById(id)?.toDomain()
    }

    override suspend fun searchLibros(query: String): List<Libro> {
        return libroDao.searchLibros("%$query%").map { it.toDomain() }
    }

    override suspend fun getLibrosByGenero(generoId: Int): List<Libro> {
        return libroDao.getLibrosByGenero(generoId).map { it.toDomain() }
    }

    override suspend fun syncLibros() {

    }
}
