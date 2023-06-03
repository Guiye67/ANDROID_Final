package baeza.guillermo.gymandyang.ui.layoutComponents

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.models.User

@Composable
fun CustomDrawer(drawerViewModel: DrawerViewModel, navCon: NavHostController, scaffoldState: ScaffoldState) {
    val user: User by drawerViewModel.user.observeAsState(User("","","","","", listOf(),""))

    LaunchedEffect(key1 = false) {
        drawerViewModel.getUser()
    }

    DrawerHeader(user.name, user.surname)
    DrawerBody(drawerViewModel, navCon, scaffoldState)
}