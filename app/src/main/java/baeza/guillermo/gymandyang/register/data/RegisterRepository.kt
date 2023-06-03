package baeza.guillermo.gymandyang.register.data

import baeza.guillermo.gymandyang.register.data.dto.RegisterDTO
import baeza.guillermo.gymandyang.register.data.network.RegisterService
import baeza.guillermo.gymandyang.ui.models.User
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val api: RegisterService
) {
    suspend fun doRegister(data: RegisterDTO): User {
        return api.doRegister(data)
    }
}