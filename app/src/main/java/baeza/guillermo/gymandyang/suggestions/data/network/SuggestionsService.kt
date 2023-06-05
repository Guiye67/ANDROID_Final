package baeza.guillermo.gymandyang.suggestions.data.network

import android.util.Log
import baeza.guillermo.gymandyang.suggestions.data.dto.SuggestionsDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SuggestionsService @Inject constructor(
    private val suggestionsClient: SuggestionsClient
) {
    suspend fun doCreate(data: SuggestionsDTO, token: String): String {
        return withContext(Dispatchers.IO) {
            val response = suggestionsClient.doCreate(token, data)

            Log.i("GYM", "data: ${response.body()}")

            if (response.isSuccessful) {
                "ok"
            } else if (response.code() == 401) {
                "Unauthorized token"
            } else {
                "Service error"
            }
        }
    }
}