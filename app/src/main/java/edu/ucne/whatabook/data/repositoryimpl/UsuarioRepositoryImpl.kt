package edu.ucne.whatabook.data.repositoryimpl
import edu.ucne.whatabook.data.local.dao.UsuarioDao
import edu.ucne.whatabook.data.local.datastore.SessionManager
import edu.ucne.whatabook.data.mappers.toDomain
import edu.ucne.whatabook.data.mappers.toEntity
import edu.ucne.whatabook.data.remote.RetrofitInstance
import edu.ucne.whatabook.data.remote.dto.UsuarioDto
import edu.ucne.whatabook.domain.model.Usuario
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val sessionManager: SessionManager
) : UsuarioRepository {

    override suspend fun login(correo: String, password: String): Usuario? {
        val usuario = usuarioDao.login(correo, password)?.toDomain()

        usuario?.let {

            sessionManager.saveSession(
                id = it.usuarioId,
                name = it.nombre,
                email = it.correo
            )
        }

        return usuario
    }

    override suspend fun register(usuario: Usuario) {
        usuarioDao.register(usuario.toEntity())
    }

    override suspend fun getUsuarioById(id: Int): Usuario? {
        return usuarioDao.getUsuarioById(id)?.toDomain()
    }

    override fun getCurrentUserId(): Flow<Int> {
        return sessionManager.userId.filterNotNull()
    }

    override fun getUserSessionId() = sessionManager.userId

    override fun getUserSessionName() = sessionManager.userName

    override fun getUserSessionEmail() = sessionManager.userEmail

    override suspend fun logout() {
        sessionManager.logout()
    }
    override suspend fun updateFotoPerfil(id: Int, uri: String) {
        usuarioDao.updateFotoPerfil(id, uri)
    }

    override suspend fun syncUsuarios() {
        try {

            val usuariosRemotos: List<UsuarioDto> = RetrofitInstance.api.getUsuarios()
            val usuariosEntity = usuariosRemotos.map { it.toEntity() }

            usuariosEntity.forEach {
                usuarioDao.register(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
