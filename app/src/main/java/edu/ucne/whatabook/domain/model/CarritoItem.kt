package edu.ucne.whatabook.domain.model


data class CarritoItem(
    val itemId: Int = 0,
    val libroId: Int = 0,
    val titulo: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 1,
    val imagenUrl: String = ""
)
