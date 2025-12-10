package edu.ucne.whatabook.data.repositoryimpl

import edu.ucne.whatabook.data.local.dao.GeneroDao
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.data.remote.WhatABookApiService
import edu.ucne.whatabook.domain.model.Genero
import edu.ucne.whatabook.domain.repository.GeneroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GeneroRepositoryImpl @Inject constructor(
    private val generoDao: GeneroDao,
    private val apiService: WhatABookApiService
) : GeneroRepository {

    override fun getGeneros(): Flow<List<Genero>> {
        return generoDao.getGeneros().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun syncGeneros() {
        try {
            val generosRemotos = apiService.getGeneros()
            val generosEntity = generosRemotos.map { it.toEntity() }
            generoDao.insertAll(generosEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}