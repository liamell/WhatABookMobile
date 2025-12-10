package edu.ucne.whatabook.presentation.libros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.usecase.libros.GetLibroByIdUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosByGeneroUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosUseCase
import edu.ucne.whatabook.domain.usecase.libros.GuardarLibroUseCase
import edu.ucne.whatabook.domain.usecase.libros.SearchLibrosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibroViewModel @Inject constructor(
    private val getLibrosUseCase: GetLibrosUseCase,
    private val getLibroByIdUseCase: GetLibroByIdUseCase,
    private val searchLibrosUseCase: SearchLibrosUseCase,
    private val getLibrosByGeneroUseCase: GetLibrosByGeneroUseCase,
    private val guardarLibroUseCase: GuardarLibroUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LibroUiState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        cargarLibros()
    }

    private fun cargarLibros() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getLibrosUseCase()
                .catch { e ->
                    _state.update { it.copy(error = e.message, isLoading = false) }
                }
                .dropWhile { libros ->
                    libros.isEmpty() && _state.value.isLoading
                }
                .collect { libros ->
                    _state.update {
                        it.copy(
                            libros = libros,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onEvent(event: LibroEvent) {
        when (event) {
            is LibroEvent.LoadLibros -> cargarLibros()
            is LibroEvent.Buscar -> buscarLibros(event.query)
            is LibroEvent.FiltrarPorGenero -> filtrarPorGenero(event.generoId)
            is LibroEvent.VerDetalle -> cargarDetalle(event.libroId)
            is LibroEvent.CrearLibro -> crearLibro(event.libro)
        }
    }

    private fun buscarLibros(query: String) {
        viewModelScope.launch {
            _state.update { it.copy(query = query) }
            val result = searchLibrosUseCase(query)
            _state.update {
                it.copy(libros = result)
            }
        }
    }

    private fun filtrarPorGenero(generoId: Int) {
        viewModelScope.launch {
            val result = getLibrosByGeneroUseCase(generoId)
            _state.update {
                it.copy(
                    libros = result,
                    generoSeleccionado = generoId
                )
            }
        }
    }

    private fun cargarDetalle(id: Int) {
        viewModelScope.launch {
            val libro = getLibroByIdUseCase(id)
            _state.update {
                it.copy(
                    libroSeleccionado = libro
                )
            }
        }
    }

    private fun crearLibro(libro: Libro) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            guardarLibroUseCase(libro)

            clearFormFields()
        }
    }

    fun onTituloChange(titulo: String) {
        _state.update { it.copy(titulo = titulo) }
    }

    fun onAutorChange(autor: String) {
        _state.update { it.copy(autor = autor) }
    }

    fun onDescripcionChange(descripcion: String) {
        _state.update { it.copy(descripcion = descripcion) }
    }

    fun onPrecioChange(precio: String) {
        val filteredPrecio = precio.filter { it.isDigit() || it == '.' }
        _state.update { it.copy(precio = filteredPrecio) }
    }

    fun onImagenUrlChange(url: String) {
        _state.update { it.copy(imagenUrl = url) }
    }

    fun onGeneroIdChange(generoId: Int) {
        _state.update { it.copy(generoId = generoId) }
    }

    fun onCantidadChange(cantidad: String) {
        val filteredCantidad = cantidad.filter { it.isDigit() }
        _state.update { it.copy(cantidad = filteredCantidad) }
    }

    fun clearFormFields() {
        _state.update {
            it.copy(
                titulo = "",
                autor = "",
                descripcion = "",
                precio = "",
                imagenUrl = "",
                cantidad = "",
                generoId = null
            )
        }
    }
}