package baeza.guillermo.gymandyang.classes.domain

import baeza.guillermo.gymandyang.classes.data.ClassesRepository
import baeza.guillermo.gymandyang.ui.models.GymClass
import javax.inject.Inject

class GetClassesUseCase @Inject constructor(private val respository: ClassesRepository) {
    suspend operator fun invoke(token: String): List<GymClass>? {
        return respository.getPosts(token)
    }
}