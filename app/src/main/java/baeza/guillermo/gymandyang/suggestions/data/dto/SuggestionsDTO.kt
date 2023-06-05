package baeza.guillermo.gymandyang.suggestions.data.dto

import com.google.gson.annotations.SerializedName

data class SuggestionsDTO(
    @SerializedName("title") val title: String,
    @SerializedName("client") val client: String,
    @SerializedName("description") val description: String,
)
