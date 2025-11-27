package edu.ucne.whatabook.data.repositoryimpl

import edu.ucne.whatabook.data.local.dao.UsuarioDao
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.domain.model.Usuario
import edu.ucne.whatabook.domain.repository.UsuarioRepository

class UsuarioRepositoryImpl(
    private val usuarioDao: UsuarioDao
) : UsuarioRepository {

    override suspend fun login(correo: String, password: String): Usuario? {
        return usuarioDao.login(correo, password)?.toDomain()
    }

    override suspend fun register(usuario: Usuario) {
        usuarioDao.register(usuario.toEntity())
    }

    override suspend fun getUsuarioById(id: Int): Usuario? {
        return usuarioDao.getUsuarioById(id)?.toDomain()
    }
}
