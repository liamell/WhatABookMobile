package edu.ucne.whatabook.presentation.carrito

import edu.ucne.whatabook.domain.model.CarritoItem

data class CartUiState(
    val items: List<CarritoItem> = emptyList(),
    val total: Double = 0.0,
    val isLoading: Boolean = false,
    val metodoPagoId: Int? = null,
    val errorMetodoPago: String? = null,
    val numeroTarjeta: String = "",
    val nombreTitular: String = "",
    val fechaExp: String = "",
    val cvv: String = "",
    val errorStock: String? = null
)
