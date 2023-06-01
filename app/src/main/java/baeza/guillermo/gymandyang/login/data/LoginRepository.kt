package baeza.guillermo.gymandyang.login.data

import baeza.guillermo.gymandyang.login.data.network.LoginService
import baeza.guillermo.gymandyang.login.data.dto.UserDTO
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginService
) {
    suspend fun doLogin(email: String, password: String): UserDTO {
        return api.doLogin(email, password)
    }
}