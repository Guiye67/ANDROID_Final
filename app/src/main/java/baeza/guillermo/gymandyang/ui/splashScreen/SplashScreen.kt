package baeza.guillermo.gymandyang.ui.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.models.Routes
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import baeza.guillermo.gymandyang.R.drawable.gymyang_logo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navCon: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        navCon.popBackStack()
        navCon.navigate(Routes.LoginScreen.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainPruple),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = gymyang_logo), contentDescription = "Gym and Yang Logo")
    }
}