package edu.ucne.whatabook.data.remote

import edu.ucne.whatabook.data.remote.api.UsuarioApi
import edu.ucne.whatabook.data.remote.dto.UsuarioDto
import javax.inject.Inject

class UsuarioRemoteDataSource @Inject constructor(
    private val api: UsuarioApi
) {
    suspend fun fetchUsuarios(): List<UsuarioDto> = api.getUsuarios()
    suspend fun createUsuario(usuario: UsuarioDto): UsuarioDto = api.createUsuario(usuario)
}