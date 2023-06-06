package baeza.guillermo.gymandyang.ui.models

data class GymClass(
    val name: String,
    val days: List<String>,
    val hour: String,
    val duration: String,
    val signedUp: List<String>,
)
