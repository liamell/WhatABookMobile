package edu.ucne.whatabook.data.mappers

import edu.ucne.whatabook.data.local.entity.UsuarioEntity
import edu.ucne.whatabook.data.remote.dto.UsuarioDto
import edu.ucne.whatabook.domain.model.Usuario

fun UsuarioDto.toEntity(): UsuarioEntity {
    return UsuarioEntity(
        usuarioId = this.usuarioId,
        nombre = this.nombre,
        correo = this.correo,
        password = this.password,
        fotoPerfilUri = this.fotoPerfilUri
    )
}

fun UsuarioDto.toDomain(): Usuario {
    return Usuario(
        usuarioId = this.usuarioId,
        nombre = this.nombre,
        correo = this.correo,
        password = this.password,
        fotoPerfilUri = this.fotoPerfilUri
    )
}
