package baeza.guillermo.gymandyang.ui.models

data class User(
    val _id: String,
    val email: String,
    val name: String,
    val surname: String,
    val payment: String,
    val classes: List<String>,
    val token: String
)
