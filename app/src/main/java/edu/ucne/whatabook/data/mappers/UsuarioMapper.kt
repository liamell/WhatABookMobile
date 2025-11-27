package edu.ucne.whatabook.data.mappers


import edu.ucne.whatabook.data.local.entity.UsuarioEntity
import edu.ucne.whatabook.domain.model.Usuario

fun UsuarioEntity.toDomain() = Usuario(
    usuarioId = usuarioId,
    nombre = nombre,
    correo = correo,
    password = password
)

fun Usuario.toEntity() = UsuarioEntity(
    usuarioId = usuarioId,
    nombre = nombre,
    correo = correo,
    password = password
)
