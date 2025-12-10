package edu.ucne.whatabook.domain.usecase.cart

import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.repository.CarritoRepository
import javax.inject.Inject

class UpdateCartItemQuantityUseCase @Inject constructor(
    private val repository: CarritoRepository
) {

    suspend operator fun invoke(itemId: Int, newQuantity: Int) {

        val itemToUpdate = repository.getCartItemById(itemId)

        if (itemToUpdate != null) {
            val updatedItem = itemToUpdate.copy(cantidad = newQuantity)

            repository.updateItem(updatedItem)
        }
    }

    suspend fun increase(item: CarritoItem) {

        repository.increaseItemQuantity(item)
    }

    suspend fun decrease(item: CarritoItem) {

        repository.decreaseItemQuantity(item)
    }
}