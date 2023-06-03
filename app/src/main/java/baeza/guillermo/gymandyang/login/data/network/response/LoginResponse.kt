package baeza.guillermo.gymandyang.login.data.network.response

import baeza.guillermo.gymandyang.ui.models.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("clientFound") val data: User,
                         @SerializedName("token") val token: String)

