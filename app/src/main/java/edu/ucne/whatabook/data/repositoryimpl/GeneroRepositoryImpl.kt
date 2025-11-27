package edu.ucne.whatabook.data.repositoryimpl


import edu.ucne.whatabook.data.local.dao.GeneroDao
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.domain.model.Genero
import edu.ucne.whatabook.domain.repository.GeneroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GeneroRepositoryImpl(
    private val generoDao: GeneroDao
) : GeneroRepository {

    override fun getGeneros(): Flow<List<Genero>> {
        return generoDao.getGeneros().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun syncGeneros() {
        // Aquí puedes simular API
    }
}
