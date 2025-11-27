package edu.ucne.whatabook.ui.libros

import edu.ucne.whatabook.domain.model.Libro

data class LibroUiState(
    val libros: List<Libro> = emptyList(),
    val libroSeleccionado: Libro? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val generoSeleccionado: Int? = null
)
