package edu.ucne.whatabook.data.mappers
import edu.ucne.whatabook.data.local.entity.GeneroEntity
import edu.ucne.whatabook.domain.model.Genero


fun GeneroEntity.toDomain() = Genero(
    generoId = generoId,
    tipoGeneros = tipoGeneros
)

fun Genero.toEntity() = GeneroEntity(
    generoId = generoId,
    tipoGeneros = tipoGeneros
)


