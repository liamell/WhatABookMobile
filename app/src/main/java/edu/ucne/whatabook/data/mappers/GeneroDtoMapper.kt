package edu.ucne.whatabook.data.mappers

import edu.ucne.whatabook.data.local.entity.GeneroEntity
import edu.ucne.whatabook.data.remote.dto.GeneroDto
import edu.ucne.whatabook.domain.model.Genero

fun GeneroDto.toEntity(): GeneroEntity {
    return GeneroEntity(
        generoId = this.generoId,
        tipoGeneros = this.tipoGeneros
    )
}

fun GeneroDto.toDomain(): Genero {
    return Genero(
        generoId = this.generoId,
        tipoGeneros = this.tipoGeneros
    )
}


fun Genero.toDto(): GeneroDto {
    return GeneroDto(
        generoId = this.generoId,
        tipoGeneros = this.tipoGeneros
    )
}