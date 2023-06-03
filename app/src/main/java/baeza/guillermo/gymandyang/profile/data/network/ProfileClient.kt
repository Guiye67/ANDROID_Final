package baeza.guillermo.gymandyang.profile.data.network

import baeza.guillermo.gymandyang.profile.data.dto.ProfileDTO
import baeza.guillermo.gymandyang.profile.data.network.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileClient {
    @PUT("/clients/{id}")
    suspend fun doUpdate(
        @Path("id") id: String,
        @Header("authorization") token: String,
        @Body data: ProfileDTO
    ): Response<ProfileResponse>
}