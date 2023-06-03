package baeza.guillermo.gymandyang.register.data.dto

import com.google.gson.annotations.SerializedName

data class RegisterDTO(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("password") val password: String
)
