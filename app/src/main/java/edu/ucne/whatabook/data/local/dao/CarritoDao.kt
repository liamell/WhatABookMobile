package edu.ucne.whatabook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import edu.ucne.whatabook.data.local.entity.CarritoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {

    @Query("SELECT * FROM carrito")
    fun getCarrito(): Flow<List<CarritoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: CarritoEntity)

    @Update
    suspend fun updateItem(item: CarritoEntity)

    @Query("DELETE FROM carrito WHERE itemId = :id")
    suspend fun removeItem(id: Int)

    @Query("DELETE FROM carrito")
    suspend fun clearCarrito()
}
