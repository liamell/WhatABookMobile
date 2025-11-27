package edu.ucne.whatabook.domain.repository


import edu.ucne.whatabook.domain.model.CarritoItem
import kotlinx.coroutines.flow.Flow

interface CarritoRepository {

    fun getCarrito(): Flow<List<CarritoItem>>

    suspend fun addItem(item: CarritoItem)

    suspend fun updateItem(item: CarritoItem)

    suspend fun removeItem(id: Int)

    suspend fun clearCarrito()
}
