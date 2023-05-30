package baeza.guillermo.gymandyang.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.model.Routes

@Composable
fun LoginScreen(navCon: NavHostController, scaffoldState: ScaffoldState, loginViewModel: LoginViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "LOGIN")
        Button(onClick = { navCon.navigate(Routes.HomeScreen.route) }) {
            Text(text = "Go to Home")
        }
    }
}