package edu.ucne.whatabook.presentation.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.data.local.entity.CompraDetalleEntity
import edu.ucne.whatabook.data.local.entity.CompraEntity
import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.usecase.cart.AddToCartUseCase
import edu.ucne.whatabook.domain.usecase.cart.ClearCartUseCase
import edu.ucne.whatabook.domain.usecase.cart.GetCartItemsUseCase
import edu.ucne.whatabook.domain.usecase.cart.RemoveFromCartUseCase
import edu.ucne.whatabook.domain.usecase.cart.UpdateCartItemQuantityUseCase
import edu.ucne.whatabook.domain.usecase.compra.RegistrarCompraUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibroByIdUseCase
import edu.ucne.whatabook.domain.usecase.libros.UpdateLibroUseCase
import edu.ucne.whatabook.domain.usecase.user.GetCurrentUserIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val getLibroByIdUseCase: GetLibroByIdUseCase,
    private val updateLibroUseCase: UpdateLibroUseCase,
    private val updateCartItemQuantityUseCase: UpdateCartItemQuantityUseCase,
    private val registrarCompraUseCase: RegistrarCompraUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState(isLoading = true))
    val uiState: StateFlow<CartUiState> = _uiState

    private var currentUserId: Int = 0

    init {
        viewModelScope.launch {
            getCurrentUserIdUseCase().collect { userId ->
                currentUserId = userId

                if (userId > 0) {
                    loadCart(userId)
                } else {
                    _uiState.update {
                        it.copy(items = emptyList(), total = 0.0, isLoading = false)
                    }
                }
            }
        }
    }

    fun selectPaymentMethod(id: Int) {
        _uiState.value = _uiState.value.copy(
            metodoPagoId = id,
            errorMetodoPago = null
        )
    }

    fun loadCart(userId: Int = currentUserId) {
        if (userId <= 0) return

        viewModelScope.launch {
            getCartItemsUseCase(userId).collect { items ->
                val total = items.sumOf { it.precio * it.cantidad }
                _uiState.value = _uiState.value.copy(
                    items = items,
                    total = total,
                    isLoading = false
                )
            }
        }
    }

    fun addLibroToCart(libro: Libro) {
        if (currentUserId <= 0) {
            _uiState.update { it.copy(errorStock = "Debes iniciar sesión para agregar ítems al carrito.") }
            return
        }

        viewModelScope.launch {
            try {
                val itemToAdd = CarritoItem(
                    libroId = libro.libroId,
                    titulo = libro.titulo,
                    precio = libro.precio,
                    cantidad = 1,
                    imagenUrl = libro.imagenUrl,
                    userId = currentUserId
                )

                addToCartUseCase(itemToAdd)
                _uiState.update { it.copy(errorStock = null) }

            } catch (e: IllegalStateException) {
                _uiState.update { it.copy(errorStock = e.message) }
            }
        }
    }

    fun increaseItemQuantity(item: CarritoItem) {
        if (currentUserId <= 0) return
        viewModelScope.launch {
            try {
                val itemWithUserId = item.copy(userId = currentUserId)
                updateCartItemQuantityUseCase.increase(itemWithUserId)
                _uiState.update { it.copy(errorStock = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorStock = e.message) }
                loadCart(currentUserId)
            }
        }
    }

    fun decreaseItemQuantity(item: CarritoItem) {
        if (currentUserId <= 0) return
        viewModelScope.launch {
            val itemWithUserId = item.copy(userId = currentUserId)
            updateCartItemQuantityUseCase.decrease(itemWithUserId)
            _uiState.update { it.copy(errorStock = null) }
        }
    }

    fun removeItem(itemId: Int) {
        if (currentUserId <= 0) return
        viewModelScope.launch {
            val itemToRemove = _uiState.value.items.find { it.itemId == itemId }

            removeFromCartUseCase(itemId)

            if (itemToRemove != null) {
                val libroActual = getLibroByIdUseCase(itemToRemove.libroId)
                if (libroActual != null) {
                    val stockDevuelto = libroActual.cantidad + itemToRemove.cantidad
                    updateLibroUseCase(libroActual.copy(cantidad = stockDevuelto))
                }
            }
        }
    }

    fun clearCart(userId: Int) {
        if (userId <= 0) return

        viewModelScope.launch {
            val itemsToReturn = _uiState.value.items

            clearCartUseCase(userId)

            itemsToReturn.forEach { item ->
                val libroActual = getLibroByIdUseCase(item.libroId)
                if (libroActual != null) {
                    val stockDevuelto = libroActual.cantidad + item.cantidad
                    updateLibroUseCase(libroActual.copy(cantidad = stockDevuelto))
                }
            }

            _uiState.update {
                it.copy(
                    items = emptyList(),
                    total = 0.0,
                    numeroTarjeta = "",
                    nombreTitular = "",
                    fechaExp = "",
                    cvv = ""
                )
            }
        }
    }

    fun confirmarCompra(userId: Int) {
        if (userId <= 0) return

        viewModelScope.launch {
            val compra = CompraEntity(
                userId = userId,
                fecha = System.currentTimeMillis(),
                total = _uiState.value.total
            )

            val detalles = _uiState.value.items.map { item ->
                CompraDetalleEntity(
                    compraId = 0,
                    libroId = item.libroId,
                    cantidad = item.cantidad,
                    precio = item.precio
                )
            }

            registrarCompraUseCase(compra, detalles)
            clearCart(userId)
        }
    }

    fun updatePaymentFields(
        numeroTarjeta: String,
        nombreTitular: String,
        fechaExp: String,
        cvv: String
    ) {
        _uiState.value = _uiState.value.copy(
            numeroTarjeta = numeroTarjeta,
            nombreTitular = nombreTitular,
            fechaExp = fechaExp,
            cvv = cvv
        )
    }
}