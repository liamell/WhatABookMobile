package edu.ucne.whatabook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros")
data class LibroEntity(
    @PrimaryKey
    val libroId: Int,
    val titulo: String,
    val autores: String,
    val descripcion: String,
    val precio: Double,
    val imagenUrl: String?,
    val generoId: Int,
    val cantidad: Int = 0
)