package edu.ucne.whatabook.domain.usecase.cart

import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.repository.CarritoRepository

class AddToCartUseCase(
    private val repository: CarritoRepository
) {
    suspend operator fun invoke(item: CarritoItem) {
        repository.addItem(item)
    }
}