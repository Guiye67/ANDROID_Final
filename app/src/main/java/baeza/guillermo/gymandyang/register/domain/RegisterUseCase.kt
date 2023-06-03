package baeza.guillermo.gymandyang.register.domain

import baeza.guillermo.gymandyang.register.data.RegisterRepository
import baeza.guillermo.gymandyang.register.data.dto.RegisterDTO
import baeza.guillermo.gymandyang.ui.models.User
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository){
    suspend operator fun invoke(data: RegisterDTO) : User {
        return repository.doRegister(data)
    }
}