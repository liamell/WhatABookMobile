package edu.ucne.whatabook.data.local
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.ucne.whatabook.data.local.dao.GeneroDao
import edu.ucne.whatabook.data.local.entity.GeneroEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class DatabaseSeeder(
    private val scope: CoroutineScope,
    private val generoDaoProvider: Provider<GeneroDao>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch(Dispatchers.IO) {
            populateGenres()
        }
    }

    private suspend fun populateGenres() {
        val generoDao = generoDaoProvider.get()

        val generos = listOf(
            GeneroEntity(generoId = 1, tipoGeneros = "Romance"),
            GeneroEntity(generoId = 2, tipoGeneros = "Fantasía"),
            GeneroEntity(generoId = 3, tipoGeneros = "Misterio"),
            GeneroEntity(generoId = 4, tipoGeneros = "Terror"),
            GeneroEntity(generoId = 5, tipoGeneros = "Accion"),
            GeneroEntity(generoId = 6, tipoGeneros = "Aventura"),
            GeneroEntity(generoId = 7, tipoGeneros = "Literatura Juvenil"),
            GeneroEntity(generoId = 8, tipoGeneros = "Ciencia Ficcion")

        )

        generoDao.insertAll(generos)
    }
}