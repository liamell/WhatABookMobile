package edu.ucne.whatabook.data.remote.dto

import com.google.gson.annotations.SerializedName
import edu.ucne.whatabook.data.remote.dto.GeneroDto

data class LibroDto(
    @SerializedName("libroId")
    val libroId: Int = 0,

    @SerializedName("titulo")
    val titulo: String?,

    @SerializedName("autor")
    val autor: String?,

    @SerializedName("descripcion")
    val descripcion: String?,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("imagenUrl")
    val imagenUrl: String?,

    @SerializedName("cantidad")
    val cantidad: Int,

    @SerializedName("generoId")
    val generoId: Int,


)