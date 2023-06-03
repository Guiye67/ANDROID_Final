package baeza.guillermo.gymandyang.register.data.network.response

import baeza.guillermo.gymandyang.ui.models.User
import com.google.gson.annotations.SerializedName

data class RegisterResponse (@SerializedName("newClient") val data: User,
                             @SerializedName("token") val token: String)