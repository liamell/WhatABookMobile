package edu.ucne.whatabook.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.whatabook.domain.repository.CompraRepository
import edu.ucne.whatabook.domain.repository.LibroRepository
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import edu.ucne.whatabook.domain.usecase.compra.GetHistorialComprasUseCase
import edu.ucne.whatabook.domain.usecase.compra.RegistrarCompraUseCase
import edu.ucne.whatabook.domain.usecase.user.LoginUseCase
import edu.ucne.whatabook.domain.usecase.user.RegisterUserUseCase
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

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: UsuarioRepository) =
        LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(repository: UsuarioRepository) =
        RegisterUserUseCase(repository)

    @Provides
    @Singleton
    fun provideRegistrarCompraUseCase(repository: CompraRepository) =
        RegistrarCompraUseCase(repository)

    @Provides
    @Singleton
    fun provideGetHistorialComprasUseCase(repository: CompraRepository) =
        GetHistorialComprasUseCase(repository)

}

