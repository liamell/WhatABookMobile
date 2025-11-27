package edu.ucne.whatabook.ui.libros

sealed class LibroEvent {
    object LoadLibros : LibroEvent()
    data class Buscar(val query: String) : LibroEvent()
    data class FiltrarPorGenero(val generoId: Int) : LibroEvent()
    data class VerDetalle(val libroId: Int) : LibroEvent()
}
