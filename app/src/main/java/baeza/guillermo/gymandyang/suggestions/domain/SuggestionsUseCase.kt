package baeza.guillermo.gymandyang.suggestions.domain

import baeza.guillermo.gymandyang.suggestions.data.SuggestionsRepository
import baeza.guillermo.gymandyang.suggestions.data.dto.SuggestionsDTO
import javax.inject.Inject

class SuggestionsUseCase @Inject constructor(private val repository: SuggestionsRepository) {
    suspend operator fun invoke(data: SuggestionsDTO, token: String): String {
        return repository.doCreate(data, token)
    }
}