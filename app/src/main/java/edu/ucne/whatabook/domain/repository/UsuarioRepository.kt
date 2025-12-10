package edu.ucne.whatabook.domain.repository
import edu.ucne.whatabook.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {

    suspend fun login(correo: String, password: String): Usuario?

    suspend fun register(usuario: Usuario)

    suspend fun getUsuarioById(id: Int): Usuario?
    fun getCurrentUserId(): Flow<Int>

    fun getUserSessionId(): Flow<Int>
    fun getUserSessionName(): Flow<String?>
    fun getUserSessionEmail(): Flow<String?>

    suspend fun logout()

    suspend fun updateFotoPerfil(userId: Int, uri: String)

    suspend fun syncUsuarios()

}
