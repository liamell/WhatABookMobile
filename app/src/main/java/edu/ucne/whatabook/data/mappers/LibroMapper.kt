package edu.ucne.whatabook.data.mappers

import edu.ucne.whatabook.data.local.entity.LibroEntity
import edu.ucne.whatabook.data.remote.dto.LibroDto
import edu.ucne.whatabook.domain.model.Libro

fun LibroEntity.toDomain() = Libro(
    libroId = libroId,
    titulo = titulo,
    autores = autores,
    descripcion = descripcion,
    precio = precio,
    imagenUrl = imagenUrl ?: "",
    generoId = generoId,
    cantidad = cantidad,
)

fun Libro.toEntity(): LibroEntity {
    return LibroEntity(
        libroId = this.libroId,
        titulo = this.titulo,
        autores = this.autores,
        descripcion = this.descripcion,
        precio = this.precio,
        imagenUrl = this.imagenUrl,
        generoId = this.generoId,
        cantidad = this.cantidad
    )
}

fun LibroDto.toEntity(): LibroEntity {
    return LibroEntity(
        libroId = this.libroId ?: 0,
        titulo = this.titulo ?: "Sin Título",
        autores = this.autor ?: "Desconocido",
        descripcion = this.descripcion ?: "",
        precio = this.precio ?: 0.0,
        imagenUrl = this.imagenUrl ?: "",
        cantidad = this.cantidad ?: 0,
        generoId = this.generoId ?: 0
    )
}

fun LibroDto.toDomain(): Libro {
    return Libro(
        libroId = this.libroId ?: 0,
        titulo = this.titulo ?: "Sin Título",
        autores = this.autor ?: "Desconocido",
        descripcion = this.descripcion ?: "",
        precio = this.precio ?: 0.0,
        imagenUrl = this.imagenUrl ?: "",
        cantidad = this.cantidad ?: 0,
        generoId = this.generoId ?: 0
    )
}

fun Libro.toDto(): LibroDto {
    return LibroDto(
        libroId = this.libroId,
        titulo = this.titulo,
        autor = this.autores,
        descripcion = this.descripcion,
        precio = this.precio,
        imagenUrl = this.imagenUrl,
        cantidad = this.cantidad,
        generoId = this.generoId
    )
}