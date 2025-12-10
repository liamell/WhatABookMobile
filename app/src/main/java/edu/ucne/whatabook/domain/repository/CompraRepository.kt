package edu.ucne.whatabook.domain.repository

import edu.ucne.whatabook.data.local.entity.CompraConDetalles
import edu.ucne.whatabook.data.local.entity.CompraDetalleEntity
import edu.ucne.whatabook.data.local.entity.CompraEntity
import kotlinx.coroutines.flow.Flow

interface CompraRepository {
    suspend fun registrarCompra(
        compra: CompraEntity,
        detalles: List<CompraDetalleEntity>
    )

    fun historialCompras(userId: Int): Flow<List<CompraConDetalles>>
}
