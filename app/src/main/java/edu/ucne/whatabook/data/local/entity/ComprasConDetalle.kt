package edu.ucne.whatabook.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CompraConDetalles(
    @Embedded val compra: CompraEntity,
    @Relation(
        parentColumn = "compraId",
        entityColumn = "compraId"
    )
    val detalles: List<CompraDetalleEntity>
)
