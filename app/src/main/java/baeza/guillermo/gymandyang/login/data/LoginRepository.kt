package baeza.guillermo.gymandyang.login.data

import baeza.guillermo.gymandyang.login.data.network.LoginService
import baeza.guillermo.gymandyang.ui.models.User
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: LoginService
) {
    suspend fun doLogin(email: String, password: String): User {
        return api.doLogin(email, password)
    }
}