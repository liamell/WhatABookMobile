package edu.ucne.whatabook.presentation.libros
import edu.ucne.whatabook.domain.model.Libro

sealed class LibroEvent {
    object LoadLibros : LibroEvent()
    data class Buscar(val query: String) : LibroEvent()
    data class FiltrarPorGenero(val generoId: Int) : LibroEvent()
    data class VerDetalle(val libroId: Int) : LibroEvent()
    data class CrearLibro(val libro: Libro) : LibroEvent()
}
