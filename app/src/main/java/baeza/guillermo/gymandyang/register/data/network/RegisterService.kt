package baeza.guillermo.gymandyang.register.data.network

import android.util.Log
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.register.data.dto.RegisterDTO
import baeza.guillermo.gymandyang.ui.models.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterService @Inject constructor(
    private val resgisterClient: RegisterClient,
    private val userPreference: UserPreferenceService
) {
    suspend fun doRegister(data: RegisterDTO) : User {
        return withContext(Dispatchers.IO) {
            val response = resgisterClient.doRegister(data)

            Log.i("Gym", "data: ${response.body()}")

            if (response.isSuccessful) {
                val user = User(
                    _id = response.body()?.data?._id!!,
                    email = response.body()?.data?.email!!,
                    name = response.body()?.data?.name!!,
                    surname = response.body()?.data?.surname!!,
                    payment = response.body()?.data?.payment!!,
                    classes = response.body()?.data?.classes!!,
                    token = response.body()?.token!!
                )
                Gson().toJson(user).let { userPreference.addUser("user", it) }
                user
            } else if (response.code() == 400) {
                User(
                    _id = "-1",
                    email = "Email not valid",
                    name = "",
                    surname = "",
                    payment = "",
                    classes = listOf(),
                    token = ""
                )
            } else if (response.code() == 409) {
                User(
                    _id = "-1",
                    email = "Email already in use",
                    name = "",
                    surname = "",
                    payment = "",
                    classes = listOf(),
                    token = ""
                )
            } else {
                User(
                    _id = "-1",
                    email = "Service error",
                    name = "",
                    surname = "",
                    payment = "",
                    classes = listOf(),
                    token = ""
                )
            }
        }
    }
}