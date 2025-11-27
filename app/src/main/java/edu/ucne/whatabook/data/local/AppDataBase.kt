package edu.ucne.whatabook.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.whatabook.data.local.dao.LibroDao
import edu.ucne.whatabook.data.local.dao.CarritoDao
import edu.ucne.whatabook.data.local.dao.GeneroDao
import edu.ucne.whatabook.data.local.dao.UsuarioDao
import edu.ucne.whatabook.data.local.entity.*

@Database(
    entities = [
        LibroEntity::class,
        UsuarioEntity::class,
        CarritoEntity::class,
        GeneroEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun libroDao(): LibroDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun carritoDao(): CarritoDao
    abstract fun generoDao(): GeneroDao
}
