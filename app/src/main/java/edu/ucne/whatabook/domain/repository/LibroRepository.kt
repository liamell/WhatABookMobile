package edu.ucne.whatabook.domain.repository

import edu.ucne.whatabook.domain.model.Libro
import kotlinx.coroutines.flow.Flow

interface LibroRepository {

    fun getLibros(): Flow<List<Libro>>

    suspend fun getLibroById(id: Int): Libro?

    suspend fun syncLibros()

    suspend fun searchLibros(query: String): List<Libro>

    suspend fun getLibrosByGenero(generoId: Int): List<Libro>
}

