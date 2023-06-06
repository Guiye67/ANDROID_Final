package baeza.guillermo.gymandyang.classes.data.network

import android.util.Log
import baeza.guillermo.gymandyang.classes.data.dto.ClassesDTO
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.ui.models.GymClass
import baeza.guillermo.gymandyang.ui.models.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClassesService @Inject constructor(
    private val classesClient: ClassesClient,
    private val userPreference: UserPreferenceService
) {
    suspend fun getClasses(token: String): List<GymClass>? {
        return withContext(Dispatchers.IO) {
            val response = classesClient.getClasses(token)

            Log.i("GYM", "data: ${response.body()}")

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }

    suspend fun updateUser(data: ClassesDTO, id: String, token: String): String {
        return withContext(Dispatchers.IO) {
            val response = classesClient.updateUser(id, token, data)

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