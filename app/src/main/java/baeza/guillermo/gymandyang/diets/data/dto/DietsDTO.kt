package baeza.guillermo.gymandyang.diets.data.dto

import com.google.gson.annotations.SerializedName

data class DietsDTO (
    @SerializedName("client") val client: String,
    @SerializedName("age") val age: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("height") val height: String,
    @SerializedName("objective") val objective: String,
    @SerializedName("allergens") val allergens: String,
)