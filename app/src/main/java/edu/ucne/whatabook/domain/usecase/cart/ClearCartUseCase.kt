package edu.ucne.whatabook.domain.usecase.cart

import edu.ucne.whatabook.domain.repository.CarritoRepository

class ClearCartUseCase(
    private val repository: CarritoRepository
) {
    suspend operator fun invoke() {
        repository.clearCarrito()
    }
}