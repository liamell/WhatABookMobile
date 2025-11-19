package edu.ucne.whatabook.data.remote



import edu.ucne.whatabook.data.remote.dto.UsuarioDto
import edu.ucne.whatabook.domain.model.Usuario
import edu.ucne.whatabook.domain.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UsuarioRepositoryImpl @Inject constructor(
    private val remote: UsuarioRemoteDataSource
) : UsuarioRepository {

    override suspend fun fetchAll(): List<Usuario> = withContext(Dispatchers.IO) {
        remote.fetchUsuarios().map { dto ->
            Usuario(dto.usuarioId, dto.userName, dto.password)
        }
    }

    override suspend fun login(userName: String, password: String): Usuario? =
        withContext(Dispatchers.IO) {
            val list = remote.fetchUsuarios()
            val found = list.find { it.userName == userName && it.password == password }
            found?.let { Usuario(it.usuarioId, it.userName, it.password) }
        }

    override suspend fun register(userName: String, password: String): Usuario =
        withContext(Dispatchers.IO) {
            val dto = UsuarioDto(0, userName, password)
            val created = remote.createUsuario(dto)
            Usuario(created.usuarioId, created.userName, created.password)
        }
}
