package baeza.guillermo.gymandyang.home.domain

import baeza.guillermo.gymandyang.home.data.HomeRepository
import baeza.guillermo.gymandyang.ui.models.GymClass
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val respository: HomeRepository) {
    suspend operator fun invoke(): List<GymClass>? {
        return respository.getClasses()
    }
}