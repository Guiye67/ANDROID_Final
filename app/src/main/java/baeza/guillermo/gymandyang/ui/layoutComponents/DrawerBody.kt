package baeza.guillermo.gymandyang.ui.layoutComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.models.Routes
import baeza.guillermo.gymandyang.R.drawable.poster
import baeza.guillermo.gymandyang.R.drawable.diet
import baeza.guillermo.gymandyang.R.drawable.thumb_up
import baeza.guillermo.gymandyang.R.drawable.profile
import baeza.guillermo.gymandyang.R.drawable.house
import baeza.guillermo.gymandyang.R.drawable.classes
import baeza.guillermo.gymandyang.ui.theme.MainRed

@Composable
fun DrawerBody(drawerViewModel: DrawerViewModel, navCon: NavHostController, scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
    ) {
        DrawerItem(
            title = "Home",
            icon = painterResource(id = house),
            description = "Home Icon",
            scaffoldState = scaffoldState
        ) {
            navCon.navigate(Routes.HomeScreen.route)
        }
        DrawerItem(
            title = "Profile",
            icon = painterResource(id = profile),
            description = "Profile Icon",
            scaffoldState = scaffoldState
        ) {
            navCon.navigate(Routes.ProfileScreen.route)
        }
        DrawerItem(
            title = "Classes",
            icon = painterResource(id = classes),
            description = "Classes Icon",
            scaffoldState = scaffoldState
        ) {
            navCon.navigate(Routes.ClassesScreen.route)
        }
        DrawerItem(
            title = "Posts",
            icon = painterResource(id = poster),
            description = "Posts Icon",
            scaffoldState = scaffoldState
        ) {
            navCon.navigate(Routes.PostsScreen.route)
        }
        DrawerItem(
            title = "Diets",
            icon = painterResource(id = diet),
            description = "Diets Icon",
            scaffoldState = scaffoldState
        ) {
            navCon.navigate(Routes.DietsScreen.route)
        }
        DrawerItem(
            title = "Suggestions",
            icon = painterResource(id = thumb_up),
            description = "Suggestions Icon",
            scaffoldState = scaffoldState
        ) {
            navCon.navigate(Routes.SuggestionsScreen.route)
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { drawerViewModel.doLogout(navCon, scaffoldState, scope) },
            modifier = Modifier
                .fillMaxWidth(0.6f),
            colors = ButtonDefaults.buttonColors(backgroundColor = MainRed),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout Icon", tint = Color.White)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "LogOut", color = Color.White)
            }
        }
    }
}