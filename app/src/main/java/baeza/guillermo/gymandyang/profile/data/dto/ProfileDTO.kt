package baeza.guillermo.gymandyang.profile.data.dto

import com.google.gson.annotations.SerializedName

data class ProfileDTO(
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("surname") val surname: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("payment") val payment: String?,
    @SerializedName("classes") val classes: List<String>?
)
