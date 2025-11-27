package edu.ucne.whatabook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class CarritoEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val libroId: Int = 0,
    val titulo: String = "",
    val precio: Double = 0.0,
    val cantidad: Int = 1,
    val imagenUrl: String = ""
)
