package edu.ucne.whatabook.domain.repository

import edu.ucne.whatabook.domain.model.CarritoItem
import kotlinx.coroutines.flow.Flow

interface CarritoRepository {

    suspend fun increaseItemQuantity(item: CarritoItem)
    suspend fun decreaseItemQuantity(item: CarritoItem)

    fun getCarrito(userId: Int): Flow<List<CarritoItem>>

    suspend fun addItem(item: CarritoItem)

    suspend fun getCartItemByLibroId(libroId: Int, userId: Int): CarritoItem?

    suspend fun removeItem(id: Int)

    suspend fun clearCart(userId: Int)

    suspend fun updateItem(item: CarritoItem)

    suspend fun getCartItemById(itemId: Int): CarritoItem?

    suspend fun performAddToCartTransaction(
        libroId: Int,
        cantidadAAgregar: Int,
        existingItem: CarritoItem?,
        newItem: CarritoItem
    )
}