package edu.ucne.whatabook.data.remote

import edu.ucne.whatabook.data.remote.dto.LibroDto
import edu.ucne.whatabook.data.remote.dto.GeneroDto
import edu.ucne.whatabook.data.remote.dto.UsuarioDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WhatABookApiService {

    @GET("libros")
    suspend fun getLibros(): List<LibroDto>

    @GET("generos")
    suspend fun getGeneros(): List<GeneroDto>

    @GET("usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>

    @POST("libros")
    suspend fun postLibro(@Body libro: LibroDto): LibroDto

    @POST("usuarios/login")
    suspend fun loginUsuario(@Body login: UsuarioDto): UsuarioDto?

    @POST("usuarios/register")
    suspend fun registerUsuario(@Body newUser: UsuarioDto): UsuarioDto?

}