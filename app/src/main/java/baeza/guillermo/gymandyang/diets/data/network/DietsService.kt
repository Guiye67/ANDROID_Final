package baeza.guillermo.gymandyang.diets.data.network

import android.util.Log
import baeza.guillermo.gymandyang.diets.data.dto.DietsDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DietsService @Inject constructor(
    private val dietsClient: DietsClient
) {
    suspend fun doCreate(data: DietsDTO, token: String): String {
        return withContext(Dispatchers.IO) {
            val response = dietsClient.doCreate(token, data)

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