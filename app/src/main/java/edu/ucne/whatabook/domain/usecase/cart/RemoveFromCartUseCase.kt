package edu.ucne.whatabook.domain.usecase.cart

import edu.ucne.whatabook.domain.repository.CarritoRepository

class RemoveFromCartUseCase(
    private val repository: CarritoRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.removeItem(id)
    }
}