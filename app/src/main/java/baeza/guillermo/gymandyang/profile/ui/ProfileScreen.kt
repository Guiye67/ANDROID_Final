package baeza.guillermo.gymandyang.profile.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.theme.MainPruple

@Composable
fun ProfileScreen(navCon: NavHostController, scaffoldState: ScaffoldState, profileViewModel: ProfileViewModel) {
    val loading:Boolean by profileViewModel.loading.observeAsState(initial = false)
    val updating:Boolean by profileViewModel.updating.observeAsState(initial = false)
    val scope = rememberCoroutineScope()

    LaunchedEffect(false) {
        profileViewModel.initUser()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                if (loading) {
                    CircularProgressIndicator(strokeWidth = 3.dp)
                }
                else if (updating) {
                    ProfileUpdater(profileViewModel, navCon, scope, scaffoldState)
                }
                else {
                    ProfileContent(profileViewModel, scope, scaffoldState)
                }
            }
        }
    )
}

@Composable
fun CustomTitle(text: String) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = MainPruple,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun CustomSpacer(num: Int) {
    Spacer(modifier = Modifier.height(num.dp))
}