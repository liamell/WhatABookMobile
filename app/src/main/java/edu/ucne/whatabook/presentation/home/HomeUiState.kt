package edu.ucne.whatabook.presentation.home

import edu.ucne.whatabook.domain.model.Libro

data class HomeUiState(
    val libros: List<Libro> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)