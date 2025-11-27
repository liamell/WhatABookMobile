package edu.ucne.whatabook.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.whatabook.domain.repository.LibroRepository
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosUseCase
import edu.ucne.whatabook.domain.usecase.libros.SearchLibrosUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibroByIdUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosByGeneroUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetLibrosUseCase(
        repository: LibroRepository
    ): GetLibrosUseCase = GetLibrosUseCase(repository)

    @Provides
    @Singleton
    fun provideSearchLibrosUseCase(
        repository: LibroRepository
    ): SearchLibrosUseCase = SearchLibrosUseCase(repository)

    @Provides
    @Singleton
    fun provideGetLibroByIdUseCase(
        repository: LibroRepository
    ): GetLibroByIdUseCase = GetLibroByIdUseCase(repository)

    @Provides
    @Singleton
    fun provideGetLibrosByGeneroUseCase(
        repository: LibroRepository
    ): GetLibrosByGeneroUseCase = GetLibrosByGeneroUseCase(repository)
}
