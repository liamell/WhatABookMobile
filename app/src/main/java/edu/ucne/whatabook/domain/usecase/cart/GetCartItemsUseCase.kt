package edu.ucne.whatabook.domain.usecase.cart

import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.repository.CarritoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CarritoRepository
) {
    operator fun invoke(userId: Int): Flow<List<CarritoItem>> {
        return repository.getCarrito(userId)
    }
}