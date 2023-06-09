package baeza.guillermo.gymandyang.profile.domain

import baeza.guillermo.gymandyang.profile.data.dto.ProfileDTO
import baeza.guillermo.gymandyang.profile.data.ProfileRepository
import baeza.guillermo.gymandyang.ui.models.User
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: ProfileRepository){
    suspend operator fun invoke(data: ProfileDTO, id: String, token: String) : String {
        return repository.doUpdate(data, id, token)
    }
}