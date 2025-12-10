package edu.ucne.whatabook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compras")
data class CompraEntity(
    @PrimaryKey(autoGenerate = true)
    val compraId: Int = 0,

    val userId: Int,
    val fecha: Long,
    val total: Double
)
