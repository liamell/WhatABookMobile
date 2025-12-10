package edu.ucne.whatabook.domain.model


data class Libro(
    val libroId: Int = 0,
    val titulo: String = "",
    val autores: String = "",
    val descripcion: String = "",
    val precio: Double = 0.0,
    val imagenUrl: String = "",
    val generoId: Int,
    val cantidad: Int

)