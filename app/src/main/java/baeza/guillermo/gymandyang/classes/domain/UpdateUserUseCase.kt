package baeza.guillermo.gymandyang.classes.domain

import baeza.guillermo.gymandyang.classes.data.ClassesRepository
import baeza.guillermo.gymandyang.classes.data.dto.ClassesDTO
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val respository: ClassesRepository) {
    suspend operator fun invoke(data: ClassesDTO, id: String, token: String): String {
        return respository.updateUser(data, id, token)
    }
}