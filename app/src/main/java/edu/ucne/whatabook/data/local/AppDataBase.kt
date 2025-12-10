package edu.ucne.whatabook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.whatabook.data.local.dao.*
import edu.ucne.whatabook.data.local.entity.*

@Database(
    entities = [
        LibroEntity::class,
        UsuarioEntity::class,
        CarritoEntity::class,
        GeneroEntity::class,
        CompraEntity::class,
        CompraDetalleEntity::class
    ],

    version = 15,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun libroDao(): LibroDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun carritoDao(): CarritoDao
    abstract fun generoDao(): GeneroDao
    abstract fun compraDao(): CompraDao

 }