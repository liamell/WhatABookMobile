package edu.ucne.whatabook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros")
data class LibroEntity(
    @PrimaryKey(autoGenerate = true)
    val libroId: Int = 0,
    val titulo: String = "",
    val autor: String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val imagenUrl: String = "",
    val generoId: Int = 0
)
