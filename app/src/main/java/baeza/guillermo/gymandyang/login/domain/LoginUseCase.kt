package baeza.guillermo.gymandyang.login.domain

import baeza.guillermo.gymandyang.login.data.LoginRepository
import baeza.guillermo.gymandyang.login.data.dto.UserDTO
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: LoginRepository){
    suspend operator fun invoke(email: String, password: String) : UserDTO {
        return repository.doLogin(email, password)
    }
}