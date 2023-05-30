package baeza.guillermo.gymandyang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import baeza.guillermo.gymandyang.home.ui.HomeScreen
import baeza.guillermo.gymandyang.home.ui.HomeViewModel
import baeza.guillermo.gymandyang.login.ui.LoginScreen
import baeza.guillermo.gymandyang.login.ui.LoginViewModel
import baeza.guillermo.gymandyang.ui.model.Routes
import baeza.guillermo.gymandyang.ui.theme.ANDROID_FinalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navCon = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            ANDROID_FinalTheme {
                NavHost(
                    navController = navCon,
                    startDestination = Routes.HomeScreen.route
                ) {
                    composable(route = Routes.LoginScreen.route) {
                        LoginScreen(navCon = navCon, scaffoldState = scaffoldState, loginViewModel = loginViewModel)
                    }
                    composable(route = Routes.HomeScreen.route) {
                        HomeScreen(navCon = navCon, scaffoldState = scaffoldState, homeViewModel = homeViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar() {

}