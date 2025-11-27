package edu.ucne.whatabook.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ucne.whatabook.data.local.entity.GeneroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneroDao {

    @Query("SELECT * FROM generos")
    fun getGeneros(): Flow<List<GeneroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeneros(list: List<GeneroEntity>)
}
