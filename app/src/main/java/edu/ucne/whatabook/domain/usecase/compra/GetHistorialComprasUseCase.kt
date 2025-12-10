package edu.ucne.whatabook.domain.usecase.compra

import edu.ucne.whatabook.domain.repository.CompraRepository
import javax.inject.Inject

class GetHistorialComprasUseCase @Inject constructor(
    private val repo: CompraRepository
) {
    operator fun invoke(userId: Int) = repo.historialCompras(userId)
}