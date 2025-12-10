package edu.ucne.whatabook.presentation.compra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.whatabook.data.local.entity.CompraConDetalles
import edu.ucne.whatabook.data.local.entity.CompraDetalleEntity
import edu.ucne.whatabook.data.local.entity.CompraEntity
import edu.ucne.whatabook.domain.model.CarritoItem
import edu.ucne.whatabook.domain.model.Libro
import edu.ucne.whatabook.domain.repository.CarritoRepository
import edu.ucne.whatabook.domain.repository.LibroRepository
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import edu.ucne.whatabook.domain.usecase.compra.GetHistorialComprasUseCase
import edu.ucne.whatabook.domain.usecase.compra.RegistrarCompraUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor(
    private val registrarCompra: RegistrarCompraUseCase,
    private val historialUseCase: GetHistorialComprasUseCase,
    private val userRepo: UsuarioRepository,
    private val libroRepo: LibroRepository,
    private val cartRepo: CarritoRepository
) : ViewModel() {

    private val _historial = MutableStateFlow<List<CompraConDetalles>>(emptyList())
    val historial: StateFlow<List<CompraConDetalles>> = _historial

    private val librosCache = mutableMapOf<Int, Libro>()

    init {
        cargarHistorial()
    }

    suspend fun getLibroByIdCached(libroId: Int): Libro? {
        return librosCache[libroId] ?: run {
            val libro = libroRepo.getLibroById(libroId)
            if (libro != null) librosCache[libroId] = libro
            libro
        }
    }

    fun registrarCompra(total: Double, items: List<CarritoItem>) {
        viewModelScope.launch {
            val userId = userRepo.getUserSessionId().first() ?: return@launch

            val compra = CompraEntity(
                userId = userId,
                fecha = System.currentTimeMillis(),
                total = total
            )

            val detalles = items.map {
                CompraDetalleEntity(
                    compraId = 0,
                    libroId = it.libroId,
                    cantidad = it.cantidad,
                    precio = it.precio
                )
            }

            registrarCompra(compra, detalles)

            cartRepo.clearCart(userId)
        }
    }

    fun cargarHistorial() {
        viewModelScope.launch {
            val userId = userRepo.getUserSessionId().first() ?: return@launch

            historialUseCase(userId).collect {
                _historial.value = it
            }
        }
    }
}