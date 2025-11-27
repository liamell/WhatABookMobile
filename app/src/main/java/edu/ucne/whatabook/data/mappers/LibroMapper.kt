package edu.ucne.whatabook.data.mappers

import edu.ucne.whatabook.data.local.entity.LibroEntity
import edu.ucne.whatabook.domain.model.Libro

fun LibroEntity.toDomain() = Libro(
    libroId = libroId,
    titulo = titulo,
    autor = autor,
    precio = precio,
    descripcion = descripcion,
    imagenUrl = imagenUrl,
    generoId = generoId
)

fun Libro.toEntity() = LibroEntity(
    libroId = libroId,
    titulo = titulo,
    autor = autor,
    precio = precio,
    descripcion = descripcion,
    imagenUrl = imagenUrl,
    generoId = generoId
)
