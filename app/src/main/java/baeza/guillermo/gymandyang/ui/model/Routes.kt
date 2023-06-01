package baeza.guillermo.gymandyang.ui.model

sealed class Routes(val route: String) {
    object LoginScreen: Routes("loginScreen")
    object HomeScreen: Routes("homeScreen")
    object RegisterScreen: Routes("registerScreen")
    object ProfileScreen: Routes("profileScreen")
    object ClassesScreen: Routes("classesScreen")
    object PostsScreen: Routes("postsScreen")
    object DietsScreen: Routes("dietsScreen")
    object SuggestionsScreen: Routes("suggestionsScreen")
}