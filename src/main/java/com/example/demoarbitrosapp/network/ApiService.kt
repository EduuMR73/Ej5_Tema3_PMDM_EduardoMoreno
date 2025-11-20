package com.example.demoarbitrosapp.network

import com.example.demoarbitrosapp.modelos.LoginRequest
import com.example.demoarbitrosapp.modelos.LoginResponse
import com.example.demoarbitrosapp.model.Arbitro
import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz que define todos los endpoints (rutas) de la API.
 * Retrofit utiliza esta interfaz para generar el código de red necesario.
 */
interface ApiService {

    /**
     * Realiza una petición POST para iniciar sesión.
     * La anotación @POST especifica la ruta relativa que se añadirá a la URL base.
     *
     * URL completa de la petición: https://arbitros-api.onrender.com/auth/login
     *
     * @param request El cuerpo de la petición, que contiene el email y la contraseña.
     * @return Un objeto Response que contiene el LoginResponse si la petición es exitosa.
     */
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    /**
     * Obtiene la lista de árbitros.
     */
    @GET("api/arbitros")
    suspend fun getArbitros(): Response<List<Arbitro>>

    /**
     * Crea un nuevo árbitro.
     */
    @POST("api/arbitros")
    suspend fun createArbitro(@Body arbitro: Arbitro): Response<Arbitro>

    /**
     * Actualiza un árbitro existente por su id.
     */
    @PUT("api/arbitros/{id}")
    suspend fun updateArbitro(@Path("id") id: Long, @Body arbitro: Arbitro): Response<Arbitro>

    /**
     * Elimina un árbitro por su id.
     */
    @DELETE("api/arbitros/{id}")
    suspend fun deleteArbitro(@Path("id") id: Long): Response<Unit>
}
