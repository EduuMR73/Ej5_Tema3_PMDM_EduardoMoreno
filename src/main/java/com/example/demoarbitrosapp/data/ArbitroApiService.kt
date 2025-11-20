package com.example.demoarbitrosapp.data

import com.example.demoarbitrosapp.model.Arbitro
import retrofit2.Response
import retrofit2.http.*

interface ArbitroApiService {
    @GET("api/arbitros")
    suspend fun getArbitros(): List<Arbitro>

    @POST("api/arbitros")
    suspend fun addArbitro(@Body arbitro: Arbitro): Response<Arbitro>

    @PUT("api/arbitros/{id}")
    suspend fun updateArbitro(@Path("id") id: Int, @Body arbitro: Arbitro): Response<Arbitro>

    @DELETE("api/arbitros/{id}")
    suspend fun deleteArbitro(@Path("id") id: Int): Response<Unit>
}
