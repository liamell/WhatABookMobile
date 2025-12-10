package edu.ucne.whatabook.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generos")
data class GeneroEntity(
    @PrimaryKey(autoGenerate = true)
    val generoId: Int = 0,
    val tipoGeneros: String = ""
)
