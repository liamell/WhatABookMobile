package edu.ucne.whatabook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import edu.ucne.whatabook.data.local.entity.LibroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroDao {

    @Query("SELECT * FROM libros")
    fun getLibros(): Flow<List<LibroEntity>>

    @Query("SELECT * FROM libros WHERE libroId = :id")
    suspend fun getLibroById(id: Int): LibroEntity?

    @Query("SELECT * FROM libros WHERE titulo LIKE :query")
    suspend fun searchLibros(query: String): List<LibroEntity>

    @Query("SELECT * FROM libros WHERE generoId = :generoId")
    suspend fun getLibrosByGenero(generoId: Int): List<LibroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(libro: LibroEntity)



}
