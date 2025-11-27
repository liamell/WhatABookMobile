package edu.ucne.whatabook.ui.libros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.whatabook.domain.usecase.libros.GetLibroByIdUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosByGeneroUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosUseCase
import edu.ucne.whatabook.domain.usecase.libros.SearchLibrosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LibroViewModel(
    private val getLibrosUseCase: GetLibrosUseCase,
    private val searchLibrosUseCase: SearchLibrosUseCase,
    private val getLibrosByGeneroUseCase: GetLibrosByGeneroUseCase,
    private val getLibroByIdUseCase: GetLibroByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LibroUiState())
    val state: StateFlow<LibroUiState> = _state

    fun onEvent(event: LibroEvent) {
        when (event) {
            LibroEvent.LoadLibros -> cargarLibros()
            is LibroEvent.Buscar -> buscarLibros(event.query)
            is LibroEvent.FiltrarPorGenero -> filtrarPorGenero(event.generoId)
            is LibroEvent.VerDetalle -> cargarDetalle(event.libroId)
        }
    }


    private fun cargarLibros() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            getLibrosUseCase().collect { lista ->
                _state.value = _state.value.copy(
                    libros = lista,
                    isLoading = false,
                    error = null
                )
            }
        }
    }



    private fun buscarLibros(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, query = query)

            val libros = searchLibrosUseCase(query)

            _state.value = _state.value.copy(
                libros = libros,
                isLoading = false,
                error = null
            )
        }
    }


    private fun filtrarPorGenero(generoId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, generoSeleccionado = generoId)

            val libros = getLibrosByGeneroUseCase(generoId)

            _state.value = _state.value.copy(
                libros = libros,
                isLoading = false,
                error = null
            )
        }
    }


    private fun cargarDetalle(libroId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val libro = getLibroByIdUseCase(libroId)

            _state.value = _state.value.copy(
                libroSeleccionado = libro,
                isLoading = false,
                error = null
            )
        }
    }
}
