package edu.ucne.whatabook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import edu.ucne.whatabook.data.local.entity.CompraConDetalles
import edu.ucne.whatabook.data.local.entity.CompraDetalleEntity
import edu.ucne.whatabook.data.local.entity.CompraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraDao {

    @Insert
    suspend fun insertCompra(compra: CompraEntity): Long

    @Insert
    suspend fun insertDetalles(detalles: List<CompraDetalleEntity>)

    @Transaction
    @Query("SELECT * FROM compras WHERE userId = :userId ORDER BY fecha DESC")
    fun getComprasConDetalles(userId: Int): Flow<List<CompraConDetalles>>
}
