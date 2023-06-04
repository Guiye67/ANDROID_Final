package baeza.guillermo.gymandyang.diets.domain

import baeza.guillermo.gymandyang.diets.data.DietsRepository
import baeza.guillermo.gymandyang.diets.data.dto.DietsDTO
import javax.inject.Inject

class DietsUseCase @Inject constructor(private val repository: DietsRepository) {
    suspend operator fun invoke(data: DietsDTO, token: String): String {
        return repository.doCreate(data, token)
    }
}