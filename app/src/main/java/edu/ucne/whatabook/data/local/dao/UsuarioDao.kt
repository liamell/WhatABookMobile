package edu.ucne.whatabook.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ucne.whatabook.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND password = :password")
    suspend fun login(correo: String, password: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE usuarioId = :id")
    suspend fun getUsuarioById(id: Int): UsuarioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun register(usuario: UsuarioEntity)
}
