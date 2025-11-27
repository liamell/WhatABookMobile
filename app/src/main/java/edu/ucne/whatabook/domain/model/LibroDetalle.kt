package edu.ucne.whatabook.domain.model


data class LibroDetalle(
    val libroId: Int = 0,
    val titulo: String = "",
    val autor: String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val imagenUrl: String = "",
    val genero: String = "",
    val paginas: Int = 0,
    val año: Int = 0
)