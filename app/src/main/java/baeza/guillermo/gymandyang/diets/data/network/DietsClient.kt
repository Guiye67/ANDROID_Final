package baeza.guillermo.gymandyang.diets.data.network

import baeza.guillermo.gymandyang.diets.data.dto.DietsDTO
import baeza.guillermo.gymandyang.diets.data.network.response.DietsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DietsClient {
    @POST("/diets/")
    suspend fun doCreate(
        @Header("authorization") token: String,
        @Body data: DietsDTO
    ): Response<DietsResponse>
}