package edu.ucne.whatabook.domain.usecase.cart

import edu.ucne.whatabook.domain.repository.CarritoRepository

class GetCartItemsUseCase(
    private val repository: CarritoRepository
) {
    suspend operator fun invoke() = repository.getCarrito()
}