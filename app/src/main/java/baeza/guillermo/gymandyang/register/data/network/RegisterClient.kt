package baeza.guillermo.gymandyang.register.data.network

import baeza.guillermo.gymandyang.register.data.dto.RegisterDTO
import baeza.guillermo.gymandyang.register.data.network.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterClient {
    @POST("/clients/")
    suspend fun doRegister(
        @Body data: RegisterDTO
    ): Response<RegisterResponse>
}