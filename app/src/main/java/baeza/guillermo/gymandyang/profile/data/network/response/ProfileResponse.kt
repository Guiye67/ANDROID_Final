package baeza.guillermo.gymandyang.profile.data.network.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(@SerializedName("name") val name: String,
                           @SerializedName("surname") val surname: String,
                           @SerializedName("email") val email: String,
                           @SerializedName("payment") val payment: String,
                           @SerializedName("classes") val classes: List<String>)
