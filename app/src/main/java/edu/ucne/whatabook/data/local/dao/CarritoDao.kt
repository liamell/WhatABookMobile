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

    @Query("SELECT * FROM carrito WHERE userId = :currentUserId")
    fun getCarrito(currentUserId: Int): Flow<List<CarritoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: CarritoEntity)

    @Update
    suspend fun updateItem(item: CarritoEntity)

    @Query("DELETE FROM carrito WHERE itemId = :id")
    suspend fun removeItem(id: Int)

    @Query("DELETE FROM carrito WHERE userId = :currentUserId")
    suspend fun clearCarrito(currentUserId: Int)

    @Query("SELECT * FROM carrito WHERE libroId = :libroId AND userId = :currentUserId LIMIT 1")
    suspend fun getCartItemByLibroId(libroId: Int, currentUserId: Int): CarritoEntity?

    @Query("SELECT * FROM carrito WHERE itemId = :itemId LIMIT 1")
    suspend fun getCartItemById(itemId: Int): CarritoEntity?


    @Query("UPDATE carrito SET cantidad = :newQuantity WHERE itemId = :itemId")
    suspend fun updateQuantity(itemId: Int, newQuantity: Int)


}
