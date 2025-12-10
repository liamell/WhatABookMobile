package edu.ucne.whatabook.domain.usecase.compra

import edu.ucne.whatabook.data.local.entity.CompraDetalleEntity
import edu.ucne.whatabook.data.local.entity.CompraEntity
import edu.ucne.whatabook.domain.repository.CompraRepository
import javax.inject.Inject

class RegistrarCompraUseCase @Inject constructor(
    private val repo: CompraRepository
) {
    suspend operator fun invoke(compra: CompraEntity, detalles: List<CompraDetalleEntity>,) {
        repo.registrarCompra(compra, detalles)
    }
}