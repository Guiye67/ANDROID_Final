package baeza.guillermo.gymandyang.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.models.Routes

@Composable
fun HomeScreen(navCon: NavHostController, scaffoldState: ScaffoldState, homeViewModel: HomeViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "HOME")
        Button(onClick = { navCon.navigate(Routes.LoginScreen.route) }) {
            Text(text = "Go to Login")
        }
    }
}