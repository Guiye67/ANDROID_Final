package baeza.guillermo.gymandyang.ui.models

sealed class Routes(val route: String) {
    object SplashScreen: Routes("splashScreen")
    object LoginScreen: Routes("loginScreen")
    object HomeScreen: Routes("homeScreen")
    object RegisterScreen: Routes("registerScreen")
    object ProfileScreen: Routes("profileScreen")
    object ClassesScreen: Routes("classesScreen")
    object PostsScreen: Routes("postsScreen")
    object DietsScreen: Routes("dietsScreen")
    object SuggestionsScreen: Routes("suggestionsScreen")
}