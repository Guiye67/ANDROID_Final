package baeza.guillermo.gymandyang.ui.layoutComponents

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.login.data.dto.UserDTO

@Composable
fun CustomDrawer(drawerViewModel: DrawerViewModel, navCon: NavHostController, scaffoldState: ScaffoldState) {
    val user:UserDTO by drawerViewModel.user.observeAsState(UserDTO("","","","","", listOf(),""))

    drawerViewModel.getUser()

    DrawerHeader(user.name, user.surname)
    DrawerBody(drawerViewModel, navCon, scaffoldState)
}