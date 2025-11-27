package edu.ucne.whatabook.domain.model


data class Libro(
    val libroId: Int = 0,
    val titulo: String = "",
    val autor: String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val imagenUrl: String = "",
    val generoId: Int = 0
)