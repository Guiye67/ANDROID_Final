package baeza.guillermo.gymandyang.diets.data.network.response

import baeza.guillermo.gymandyang.diets.data.dto.DietsDTO
import com.google.gson.annotations.SerializedName

data class DietsResponse (@SerializedName("newDiet") val data: DietsDTO)