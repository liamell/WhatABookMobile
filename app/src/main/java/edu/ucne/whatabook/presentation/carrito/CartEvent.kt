package edu.ucne.whatabook.presentation.carrito

sealed class CarritoEvent {
    object CargarCarrito : CarritoEvent()
    data class EliminarItem(val itemId: Int) : CarritoEvent()
    object VaciarCarrito : CarritoEvent()
    data class SeleccionarMetodo(val metodo: String) : CarritoEvent()
    data class NumeroTarjetaChange(val value: String) : CarritoEvent()
    data class NombreTitularChange(val value: String) : CarritoEvent()
    data class FechaExpChange(val value: String) : CarritoEvent()
    data class CvvChange(val value: String) : CarritoEvent()
    object FinalizarCompra : CarritoEvent()
}