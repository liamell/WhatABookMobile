package edu.ucne.whatabook.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.whatabook.data.local.dao.LibroDao
import edu.ucne.whatabook.data.repositoryimpl.LibroRepositoryImpl
import edu.ucne.whatabook.domain.repository.LibroRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLibroRepository(
        dao: LibroDao
    ): LibroRepository = LibroRepositoryImpl(dao)
}
