package baeza.guillermo.gymandyang.home.data.network

import android.util.Log
import baeza.guillermo.gymandyang.ui.models.GymClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeService @Inject constructor(
    private val homeClient: HomeClient
) {
    suspend fun getClasses(id: String, token: String): List<GymClass>? {
        return withContext(Dispatchers.IO) {
            val response = homeClient.getClasses(id, token)

            Log.i("GYM", "data: ${response.body()}")

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}