package edu.ucne.whatabook.domain.repository

import edu.ucne.whatabook.domain.model.Genero
import kotlinx.coroutines.flow.Flow

interface GeneroRepository {

    fun getGeneros(): Flow<List<Genero>>

    suspend fun syncGeneros()
}
