package edu.ucne.whatabook.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.whatabook.data.local.AppDatabase
import edu.ucne.whatabook.data.local.DatabaseSeeder
import edu.ucne.whatabook.data.local.dao.CarritoDao
import edu.ucne.whatabook.data.local.dao.CompraDao
import edu.ucne.whatabook.data.local.dao.GeneroDao
import edu.ucne.whatabook.data.local.dao.LibroDao
import edu.ucne.whatabook.data.local.dao.UsuarioDao
import edu.ucne.whatabook.data.remote.WhatABookApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        generoDaoProvider: Provider<GeneroDao>,
        scope: CoroutineScope
    ): AppDatabase {

        val seeder = DatabaseSeeder(
            scope,
            generoDaoProvider
        )

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "whatabook.db"
        )
            .fallbackToDestructiveMigration()
            .addCallback(seeder)
            .build()
    }

    @Provides
    @Singleton
    fun provideLibroDao(db: AppDatabase): LibroDao = db.libroDao()

    @Provides
    @Singleton
    fun provideGeneroDao(db: AppDatabase): GeneroDao = db.generoDao()

    @Provides
    @Singleton
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao = db.usuarioDao()

    @Provides
    @Singleton
    fun provideCarritoDao(db: AppDatabase): CarritoDao = db.carritoDao()

    @Provides
    @Singleton
    fun provideCompraDao(db: AppDatabase): CompraDao = db.compraDao()

    @Provides
    @Singleton
    fun provideApiService(): WhatABookApiService {
        return Retrofit.Builder()
            .baseUrl("http://WhatABook.somee.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WhatABookApiService::class.java)
    }
}