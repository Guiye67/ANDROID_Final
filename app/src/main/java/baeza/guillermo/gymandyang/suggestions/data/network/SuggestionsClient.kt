package baeza.guillermo.gymandyang.suggestions.data.network

import baeza.guillermo.gymandyang.suggestions.data.dto.SuggestionsDTO
import baeza.guillermo.gymandyang.suggestions.data.network.response.SuggestionsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SuggestionsClient {
    @POST("/suggestions/")
    suspend fun doCreate(
        @Header("authorization") token: String,
        @Body data: SuggestionsDTO
    ): Response<SuggestionsResponse>
}