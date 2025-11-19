package edu.ucne.whatabook.data.remote.api


import edu.ucne.whatabook.data.remote.dto.UsuarioDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface UsuarioApi {
    @GET("api/Usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>


    @POST("api/Usuarios")
    suspend fun createUsuario(@Body usuario: UsuarioDto): UsuarioDto

//
//  if server supported login endpoint that returns token
// @POST("api/Auth/login")
// suspend fun login(@Body request: LoginRequest): TokenResponse
}