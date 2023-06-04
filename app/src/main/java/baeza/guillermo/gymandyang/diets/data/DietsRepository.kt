package baeza.guillermo.gymandyang.diets.data

import baeza.guillermo.gymandyang.diets.data.dto.DietsDTO
import baeza.guillermo.gymandyang.diets.data.network.DietsService
import javax.inject.Inject

class DietsRepository @Inject constructor(
    private val api: DietsService
) {
    suspend fun doCreate(data: DietsDTO, token: String): String {
        return api.doCreate(data, token)
    }
}