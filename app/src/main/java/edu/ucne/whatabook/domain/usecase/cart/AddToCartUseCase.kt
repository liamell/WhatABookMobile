package edu.ucne.whatabook.domain.usecase.cart
import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.repository.CarritoRepository
import edu.ucne.whatabook.domain.repository.LibroRepository
import javax.inject.Inject


class AddToCartUseCase @Inject constructor(
    private val repository: CarritoRepository,
    private val libroRepository: LibroRepository
) {
    suspend operator fun invoke(newItem: CarritoItem) {

        val cantidadAAgregar = 1

        val existingItem = repository.getCartItemByLibroId(
            libroId = newItem.libroId,
            userId = newItem.userId
        )


        repository.performAddToCartTransaction(
            libroId = newItem.libroId,
            cantidadAAgregar = cantidadAAgregar,
            existingItem = existingItem,
            newItem = newItem
        )
    }
}