package baeza.guillermo.gymandyang.home.data.network

import baeza.guillermo.gymandyang.ui.models.GymClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HomeClient {
    @GET("/classes/signedUp/{id}")
    suspend fun getClasses(
        @Path("id") id: String,
        @Header("authorization") token: String
    ): Response<List<GymClass>>
}