package baeza.guillermo.gymandyang.ui.model

sealed class Routes(val route: String) {
    object LoginScreen: Routes("loginScreen")
    object HomeScreen: Routes("homeScreen")
}