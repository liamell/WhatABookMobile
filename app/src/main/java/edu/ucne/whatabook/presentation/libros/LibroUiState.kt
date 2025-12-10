package edu.ucne.whatabook.presentation.libros
import edu.ucne.whatabook.domain.model.Libro

data class LibroUiState(
    val libros: List<Libro> = emptyList(),
    val libroSeleccionado: Libro? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val generoSeleccionado: Int? = null,


    val titulo: String = "",
    val autor: String = "",
    val descripcion: String = "",
    val generoId: Int? = null,
    val precio: String = "",
    val imagenUrl: String = "",
    val cantidad: String = ""
)
