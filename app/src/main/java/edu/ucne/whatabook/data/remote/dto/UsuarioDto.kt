package edu.ucne.whatabook.data.remote.dto

data class UsuarioDto(
    val usuarioId: Int = 0,
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val fotoPerfilUri: String? = null
)
