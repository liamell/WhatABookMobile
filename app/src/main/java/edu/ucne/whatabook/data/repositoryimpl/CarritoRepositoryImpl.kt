package edu.ucne.whatabook.data.repositoryimpl

import edu.ucne.whatabook.data.local.dao.CarritoDao
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.repository.CarritoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CarritoRepositoryImpl(
    private val carritoDao: CarritoDao
) : CarritoRepository {

    override fun getCarrito(): Flow<List<CarritoItem>> {
        return carritoDao.getCarrito().map { list ->
            list.map { it.toDomain() }
        }
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

    override suspend fun clearCarrito() {
        carritoDao.clearCarrito()
    }
}
