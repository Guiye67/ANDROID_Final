package baeza.guillermo.gymandyang.classes.data.network

import baeza.guillermo.gymandyang.classes.data.dto.ClassesDTO
import baeza.guillermo.gymandyang.classes.data.network.response.ClassesResponse
import baeza.guillermo.gymandyang.ui.models.GymClass
import retrofit2.Response
import retrofit2.http.*

interface ClassesClient {
    @GET("/classes/")
    suspend fun getClasses(
        @Header("authorization") token: String
    ): Response<List<GymClass>>

    @PUT("/clients/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Header("authorization") token: String,
        @Body data: ClassesDTO
    ): Response<ClassesResponse>
}