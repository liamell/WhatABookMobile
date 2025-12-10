package edu.ucne.whatabook.data.repositoryimpl

import edu.ucne.whatabook.data.local.dao.CompraDao
import edu.ucne.whatabook.data.local.entity.CompraConDetalles
import edu.ucne.whatabook.data.local.entity.CompraDetalleEntity
import edu.ucne.whatabook.data.local.entity.CompraEntity
import edu.ucne.whatabook.domain.repository.CompraRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompraRepositoryImpl @Inject constructor(
    private val dao: CompraDao
) : CompraRepository {

    @androidx.room.Transaction
    override suspend fun registrarCompra(compra: CompraEntity, detalles: List<CompraDetalleEntity>) {

        val compraId = dao.insertCompra(compra).toInt()

        val detallesConId = detalles.map { it.copy(compraId = compraId) }

        dao.insertDetalles(detallesConId)
    }

    override fun historialCompras(userId: Int): Flow<List<CompraConDetalles>> {
        return dao.getComprasConDetalles(userId)
    }
}
