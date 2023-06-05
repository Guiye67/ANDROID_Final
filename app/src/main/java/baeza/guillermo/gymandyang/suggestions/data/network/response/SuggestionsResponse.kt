package baeza.guillermo.gymandyang.suggestions.data.network.response

import baeza.guillermo.gymandyang.suggestions.data.dto.SuggestionsDTO
import com.google.gson.annotations.SerializedName

data class SuggestionsResponse(@SerializedName("newSuggestion") val data: SuggestionsDTO)
