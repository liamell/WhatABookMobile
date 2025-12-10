package edu.ucne.whatabook.domain.usecase.cart

import edu.ucne.whatabook.domain.repository.CarritoRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val repository: CarritoRepository
) {
    suspend operator fun invoke(userId: Int) {
        repository.clearCart(userId)
    }
}