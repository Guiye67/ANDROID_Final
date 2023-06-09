package baeza.guillermo.gymandyang.profile.data.network

import android.util.Log
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.profile.data.dto.ProfileDTO
import baeza.guillermo.gymandyang.ui.models.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileService @Inject constructor(
    private val profileClient: ProfileClient,
    private val userPreference: UserPreferenceService
) {
    suspend fun doUpdate(data: ProfileDTO, id: String, token: String): String {
        return withContext(Dispatchers.IO) {
            val response = profileClient.doUpdate(id, token, data)

            Log.i("GYM", "data: ${response.body()}")

            if (response.isSuccessful) {
                val user = User(
                    _id = id,
                    email = response.body()?.email!!,
                    name = response.body()?.name!!,
                    surname = response.body()?.surname!!,
                    payment = response.body()?.payment!!,
                    classes = response.body()?.classes!!,
                    token = token
                )
                Gson().toJson(user).let { userPreference.addUser("user", it) }
                "ok"
            } else if (response.code() == 401) {
                "Unauthorized token"
            } else if (response.code() == 404) {
                "Cannot find client"
            } else if (response.code() == 400) {
                "Email not valid"
            } else if (response.code() == 409) {
                "Email already in use"
            } else {
                "Service error"
            }
        }
    }
}