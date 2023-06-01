package baeza.guillermo.gymandyang.login.data.network

import baeza.guillermo.gymandyang.login.data.dto.LoginDTO
import baeza.guillermo.gymandyang.login.data.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginClient {
    @POST("/clients/login")
    suspend fun doLogin(
        @Body data: LoginDTO
    ): Response<LoginResponse>
}