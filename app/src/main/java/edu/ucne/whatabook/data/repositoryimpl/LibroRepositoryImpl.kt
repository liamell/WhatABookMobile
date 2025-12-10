package edu.ucne.whatabook.data.repositoryimpl

import edu.ucne.whatabook.data.local.dao.LibroDao
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.data.mappers.toDto
import edu.ucne.whatabook.data.remote.WhatABookApiService
import edu.ucne.whatabook.data.remote.dto.LibroDto
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.repository.LibroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import android.util.Log

class LibroRepositoryImpl @Inject constructor(
    private val libroDao: LibroDao,
    private val apiService: WhatABookApiService

) : LibroRepository {

    override fun getLibros(): Flow<List<Libro>> =
        libroDao
            .getLibros()
            .map { listaEntity ->
                listaEntity.map { libroEntity ->
                    libroEntity.toDomain()
                }
            }
            .onStart { syncLibros() }

    override suspend fun getLibroById(id: Int): Libro? {
        val libro = libroDao.getLibroById(id) ?: return null
        return libro.toDomain()
    }

    override suspend fun searchLibros(query: String): List<Libro> =
        libroDao.searchLibros("%$query%").map { it.toDomain() }

    override suspend fun getLibrosByGenero(generoId: Int): List<Libro> {
        return libroDao.getLibrosByGenero(generoId).map {
            it.toDomain()
        }
    }

    override suspend fun insert(libro: Libro) {
        libroDao.insert(libro.toEntity())
    }

    override suspend fun insertAll(libros: List<Libro>) {
        libroDao.insertAll(libros.map { it.toEntity() })
    }

    override suspend fun delete(id: Int) {
        libroDao.deleteLibro(id)
    }

    override suspend fun updateLibro(libro: Libro) {
        libroDao.updateLibro(libro.toEntity())
    }

    override suspend fun insertRemoto(libro: Libro): Boolean {
        return try {
            val dto: LibroDto = libro.toDto()
            val remoteBookDto = apiService.postLibro(dto)
            libroDao.insert(remoteBookDto.toEntity())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            libroDao.insert(libro.toEntity())
            false
        }
    }

    override suspend fun syncLibros() {
        try {
            val librosRemotos = apiService.getLibros()

            if (librosRemotos.isNotEmpty()) {
                val entidades = librosRemotos.map { it.toEntity() }
                libroDao.replaceAll(entidades)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("REPO_ERROR", "Fallo en syncLibros: ${e.message}")
        }
    }
}