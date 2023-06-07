package baeza.guillermo.gymandyang.home.data

import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.home.data.network.HomeService
import baeza.guillermo.gymandyang.ui.models.GymClass
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeService,
    private val userPreferenceService: UserPreferenceService
) {
    suspend fun getClasses(): List<GymClass>? {
        val token = userPreferenceService.getUser("user")!!.token
        val id = userPreferenceService.getUser("user")!!._id

        return api.getClasses(id, token)
    }
}