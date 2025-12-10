package edu.ucne.whatabook.domain.model


data class Usuario(
    val usuarioId: Int = 0,
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val fotoPerfilUri: String? = null
)
