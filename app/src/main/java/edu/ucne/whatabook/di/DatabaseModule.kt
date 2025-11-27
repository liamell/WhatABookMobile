package edu.ucne.whatabook.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.whatabook.data.local.AppDatabase
import edu.ucne.whatabook.data.local.dao.CarritoDao
import edu.ucne.whatabook.data.local.dao.GeneroDao
import edu.ucne.whatabook.data.local.dao.LibroDao
import edu.ucne.whatabook.data.local.dao.UsuarioDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "whatabook.db"
        ).build()
    }

    @Provides
    fun provideLibroDao(db: AppDatabase): LibroDao = db.libroDao()

    @Provides
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao = db.usuarioDao()

    @Provides
    fun provideCarritoDao(db: AppDatabase): CarritoDao = db.carritoDao()

    @Provides
    fun provideGeneroDao(db: AppDatabase): GeneroDao = db.generoDao()
}
