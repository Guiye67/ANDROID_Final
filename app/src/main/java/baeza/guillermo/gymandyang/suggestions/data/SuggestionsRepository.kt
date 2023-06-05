package baeza.guillermo.gymandyang.suggestions.data

import baeza.guillermo.gymandyang.suggestions.data.dto.SuggestionsDTO
import baeza.guillermo.gymandyang.suggestions.data.network.SuggestionsService
import javax.inject.Inject

class SuggestionsRepository @Inject constructor(
    private val api: SuggestionsService
) {
    suspend fun doCreate(data: SuggestionsDTO, token: String): String {
        return api.doCreate(data, token)
    }
}