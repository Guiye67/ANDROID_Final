package baeza.guillermo.gymandyang.login.data.network.response

import baeza.guillermo.gymandyang.login.data.dto.UserDTO
import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("clientFound") val data: UserDTO,
                         @SerializedName("token") val token: String)

