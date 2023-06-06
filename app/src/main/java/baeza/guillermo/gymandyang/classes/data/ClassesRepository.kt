package baeza.guillermo.gymandyang.classes.data

import baeza.guillermo.gymandyang.classes.data.dto.ClassesDTO
import baeza.guillermo.gymandyang.classes.data.network.ClassesService
import baeza.guillermo.gymandyang.ui.models.GymClass
import javax.inject.Inject

class ClassesRepository@Inject constructor(
    private val api: ClassesService,
) {
    suspend fun getPosts(token: String): List<GymClass>? {
        return api.getClasses(token)
    }

    suspend fun updateUser(data: ClassesDTO, id: String, token: String): String {
        return api.updateUser(data, id, token)
    }
}