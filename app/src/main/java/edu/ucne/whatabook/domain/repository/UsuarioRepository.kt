package edu.ucne.whatabook.domain.repository

import edu.ucne.whatabook.domain.model.Usuario

interface UsuarioRepository {

    suspend fun login(correo: String, password: String): Usuario?

    suspend fun register(usuario: Usuario)

    suspend fun getUsuarioById(id: Int): Usuario?



}
