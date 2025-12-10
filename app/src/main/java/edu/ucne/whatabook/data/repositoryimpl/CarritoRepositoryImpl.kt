package edu.ucne.whatabook.data.repositoryimpl

import androidx.room.withTransaction
import edu.ucne.whatabook.data.local.AppDatabase
import edu.ucne.whatabook.data.local.dao.CarritoDao
import edu.ucne.whatabook.data.local.dao.LibroDao
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.repository.CarritoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CarritoRepositoryImpl @Inject constructor(
    private val carritoDao: CarritoDao,
    private val libroDao: LibroDao,
    private val database: AppDatabase
) : CarritoRepository {

    override fun getCarrito(userId: Int): Flow<List<CarritoItem>> {
        return carritoDao.getCarrito(userId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getCartItemByLibroId(libroId: Int, userId: Int): CarritoItem? {
        return carritoDao.getCartItemByLibroId(libroId, userId)?.toDomain()
    }

    override suspend fun addItem(item: CarritoItem) {
        carritoDao.addItem(item.toEntity())
    }

    override suspend fun updateItem(item: CarritoItem) {
        carritoDao.updateItem(item.toEntity())
    }

    override suspend fun removeItem(id: Int) {
        carritoDao.removeItem(id)
    }

    override suspend fun clearCart(userId: Int) {
        carritoDao.clearCarrito(userId)
    }

    override suspend fun getCartItemById(itemId: Int): CarritoItem? {
        return carritoDao.getCartItemById(itemId)?.toDomain()
    }

    override suspend fun performAddToCartTransaction(
        libroId: Int,
        cantidadAAgregar: Int,
        existingItem: CarritoItem?,
        newItem: CarritoItem
    ) {
        database.withTransaction {

            val libroEnStock = libroDao.getLibroById(libroId)?.toDomain()

            if (libroEnStock == null || libroEnStock.cantidad < cantidadAAgregar) {
                throw IllegalStateException("El libro '${newItem.titulo}' ha agotado el stock disponible para añadir otra unidad.")
            }

            val nuevoStockLibro = libroEnStock.cantidad - cantidadAAgregar

            libroDao.updateLibro(libroEnStock.copy(cantidad = nuevoStockLibro).toEntity())

            if (existingItem != null) {
                val nuevaCantidad = existingItem.cantidad + cantidadAAgregar
                val updatedItem = existingItem.copy(
                    cantidad = nuevaCantidad,
                    precio = newItem.precio,
                    imagenUrl = newItem.imagenUrl
                )
                carritoDao.updateItem(updatedItem.toEntity())
            } else {
                carritoDao.addItem(newItem.toEntity())
            }
        }
    }

    override suspend fun increaseItemQuantity(item: CarritoItem) {
        database.withTransaction {
            val libroEntity = libroDao.getLibroById(item.libroId)

            if (libroEntity == null || libroEntity.cantidad < 1) {
                throw IllegalStateException("No hay más stock disponible para ${item.titulo}.")
            }

            val nuevoStockLibro = libroEntity.cantidad - 1

            libroDao.updateLibro(libroEntity.copy(cantidad = nuevoStockLibro))

            val nuevaCantidadCarrito = item.cantidad + 1
            carritoDao.updateQuantity(item.itemId, nuevaCantidadCarrito)
        }
    }

    override suspend fun decreaseItemQuantity(item: CarritoItem) {
        database.withTransaction {
            val libroEntity = libroDao.getLibroById(item.libroId)

            libroEntity?.let {
                val nuevoStockLibro = it.cantidad + 1
                libroDao.updateLibro(it.copy(cantidad = nuevoStockLibro))
            }

            val nuevaCantidadCarrito = item.cantidad - 1

            if (nuevaCantidadCarrito < 1) {
                carritoDao.removeItem(item.itemId)
            } else {
                carritoDao.updateQuantity(item.itemId, nuevaCantidadCarrito)
            }
        }
    }
}