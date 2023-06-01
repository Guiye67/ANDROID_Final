package baeza.guillermo.gymandyang.login.data.dto

data class UserDTO(
    val _id: String,
    val email: String,
    val name: String,
    val surname: String,
    val payment: String,
    val classes: List<String>,
    val token: String
)
