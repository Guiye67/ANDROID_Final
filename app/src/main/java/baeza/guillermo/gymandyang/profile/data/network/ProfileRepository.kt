package baeza.guillermo.gymandyang.profile.data.network

import baeza.guillermo.gymandyang.profile.data.ProfileService
import baeza.guillermo.gymandyang.profile.data.dto.ProfileDTO
import baeza.guillermo.gymandyang.ui.models.User
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileService
) {
    suspend fun doUpdate(data: ProfileDTO, id: String, token: String): User {
        return api.doUpdate(data, id, token)
    }
}