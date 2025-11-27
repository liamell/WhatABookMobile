package edu.ucne.whatabook.data.mappers


import edu.ucne.whatabook.data.local.entity.CarritoEntity
import edu.ucne.whatabook.domain.model.CarritoItem

fun CarritoEntity.toDomain() = CarritoItem(
    itemId = itemId,
    libroId = libroId,
    titulo = titulo,
    precio = precio,
    cantidad = cantidad,
    imagenUrl = imagenUrl
)

fun CarritoItem.toEntity() = CarritoEntity(
    itemId = itemId,
    libroId = libroId,
    titulo = titulo,
    precio = precio,
    cantidad = cantidad,
    imagenUrl = imagenUrl
)
