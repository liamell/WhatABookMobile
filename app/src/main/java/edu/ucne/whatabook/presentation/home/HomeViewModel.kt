package edu.ucne.whatabook.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosUseCase
import edu.ucne.whatabook.domain.usecase.libros.SearchLibrosUseCase
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosByGeneroUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLibrosUseCase: GetLibrosUseCase,
    private val searchLibrosUseCase: SearchLibrosUseCase,
    private val getLibrosByGeneroUseCase: GetLibrosByGeneroUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allLibrosCache: List<Libro> = emptyList()

    init {
        loadLibros()
    }

    fun loadLibros() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getLibrosUseCase()
                .catch { e ->
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
                .dropWhile { lista ->
                    lista.isEmpty() && _uiState.value.isLoading
                }
                .collect { lista ->
                    val librosValidos = lista.filter { libro ->
                        !libro.titulo.isNullOrBlank()
                    }

                    allLibrosCache = librosValidos.toList()
                    _uiState.update {
                        it.copy(
                            libros = librosValidos,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                _uiState.update { it.copy(libros = allLibrosCache) }
            } else {
                val result = searchLibrosUseCase(query)
                _uiState.update { it.copy(libros = result) }
            }
        }
    }

    fun filtrarPorGenero(generoId: Int) {
        viewModelScope.launch {
            if (generoId == 0) {
                _uiState.update { it.copy(libros = allLibrosCache) }
            } else {
                val lista = getLibrosByGeneroUseCase(generoId)
                _uiState.update { it.copy(libros = lista) }
            }
        }
    }
}