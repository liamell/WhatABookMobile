package edu.ucne.whatabook.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "compras_detalle",
    foreignKeys = [
        ForeignKey(
            entity = CompraEntity::class,
            parentColumns = ["compraId"],
            childColumns = ["compraId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CompraDetalleEntity(
    @PrimaryKey(autoGenerate = true)
    val detalleId: Int = 0,

    val compraId: Int,
    val libroId: Int,
    val cantidad: Int,
    val precio: Double
)
