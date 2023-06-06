package baeza.guillermo.gymandyang.classes.data.network.response

import com.google.gson.annotations.SerializedName

data class ClassesResponse(@SerializedName("name") val name: String,
                           @SerializedName("surname") val surname: String,
                           @SerializedName("email") val email: String,
                           @SerializedName("payment") val payment: String,
                           @SerializedName("classes") val classes: List<String>)
