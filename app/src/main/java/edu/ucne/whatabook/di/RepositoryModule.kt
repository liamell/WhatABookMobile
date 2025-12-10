package edu.ucne.whatabook.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.whatabook.data.local.AppDatabase
import edu.ucne.whatabook.data.local.dao.*
import edu.ucne.whatabook.data.local.datastore.SessionManager
import edu.ucne.whatabook.data.remote.WhatABookApiService
import edu.ucne.whatabook.data.repositoryimpl.CarritoRepositoryImpl
import edu.ucne.whatabook.data.repositoryimpl.CompraRepositoryImpl
import edu.ucne.whatabook.data.repositoryimpl.GeneroRepositoryImpl
import edu.ucne.whatabook.data.repositoryimpl.LibroRepositoryImpl
import edu.ucne.whatabook.data.repositoryimpl.UsuarioRepositoryImpl
import edu.ucne.whatabook.domain.repository.CarritoRepository
import edu.ucne.whatabook.domain.repository.CompraRepository
import edu.ucne.whatabook.domain.repository.GeneroRepository
import edu.ucne.whatabook.domain.repository.LibroRepository
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLibroRepository(
        libroDao: LibroDao,
        apiService: WhatABookApiService
    ): LibroRepository = LibroRepositoryImpl(
        libroDao,
        apiService
    )

    @Provides
    @Singleton
    fun provideGeneroRepository(
        generoDao: GeneroDao,
        apiService: WhatABookApiService
    ): GeneroRepository = GeneroRepositoryImpl(
        generoDao,
        apiService
    )

    @Provides
    @Singleton
    fun provideCarritoRepository(
        carritoDao: CarritoDao,
        libroDao: LibroDao,
        database: AppDatabase
    ): CarritoRepository = CarritoRepositoryImpl(
        carritoDao,
        libroDao,
        database
    )

    @Provides
    @Singleton
    fun provideUsuarioRepository(
        usuarioDao: UsuarioDao,
        sessionManager: SessionManager
    ): UsuarioRepository = UsuarioRepositoryImpl(
        usuarioDao,
        sessionManager
    )

    @Provides
    @Singleton
    fun provideCompraRepository(
        impl: CompraRepositoryImpl
    ): CompraRepository = impl
}