package edu.ucne.whatabook.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosUseCase
import edu.ucne.whatabook.domain.usecase.libros.SearchLibrosUseCase
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import edu.ucne.whatabook.domain.usecase.libros.GetLibrosByGeneroUseCase
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLibrosUseCase: GetLibrosUseCase,
    private val searchLibrosUseCase: SearchLibrosUseCase,
    private val getLibrosByGeneroUseCase: GetLibrosByGeneroUseCase
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadLibros()
    }

    fun loadLibros() {
        viewModelScope.launch {
            getLibrosUseCase().collect { lista ->
                uiState = uiState.copy(
                    libros = lista
                )
            }
        }
    }


    fun search(query: String) {
        viewModelScope.launch {
            val result = searchLibrosUseCase(query)
            uiState = uiState.copy(
                libros = result
            )
        }
    }

    fun filtrarPorGenero(generoId: Int) {
        viewModelScope.launch {
            val lista = getLibrosByGeneroUseCase(generoId)
            uiState = uiState.copy(
                libros = lista
            )
        }
    }

}

data class HomeUiState(
    val libros: List<Libro> = emptyList()
)
