package baeza.guillermo.gymandyang.login.domain

import baeza.guillermo.gymandyang.login.data.LoginRepository
import baeza.guillermo.gymandyang.ui.models.User
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository){
    suspend operator fun invoke(email: String, password: String) : User {
        return repository.doLogin(email, password)
    }
}