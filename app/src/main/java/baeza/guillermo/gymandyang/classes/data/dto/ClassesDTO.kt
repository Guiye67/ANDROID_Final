package baeza.guillermo.gymandyang.classes.data.dto

import com.google.gson.annotations.SerializedName

data class ClassesDTO(
    @SerializedName("email") val email: String,
    @SerializedName("classes") val classes: List<String>
)
