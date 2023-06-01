package baeza.guillermo.gymandyang.login.data.network

import android.util.Log
import baeza.guillermo.gymandyang.login.data.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.login.data.dto.LoginDTO
import baeza.guillermo.gymandyang.login.data.dto.UserDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(
    private val loginClient: LoginClient,
    private val userPreference: UserPreferenceService
) {
    suspend fun doLogin(email: String, password: String) : UserDTO {
        return withContext(Dispatchers.IO) {
            val data = LoginDTO(email, password)
            val response = loginClient.doLogin(data)

            Log.i("Gym", "data: ${response.body()}")

            if (response.isSuccessful) {
                val user = UserDTO(
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
            } else if (response.code() == 401) {
                UserDTO(
                    _id = "-1",
                    email = "Invalid password",
                    name = "",
                    surname = "",
                    payment = "",
                    classes = listOf(),
                    token = ""
                )
            } else if (response.code() == 404) {
                UserDTO(
                    _id = "-1",
                    email = "Cannot find client",
                    name = "",
                    surname = "",
                    payment = "",
                    classes = listOf(),
                    token = ""
                )
            } else {
                UserDTO(
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