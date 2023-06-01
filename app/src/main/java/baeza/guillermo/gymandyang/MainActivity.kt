package baeza.guillermo.gymandyang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import baeza.guillermo.gymandyang.home.ui.HomeScreen
import baeza.guillermo.gymandyang.home.ui.HomeViewModel
import baeza.guillermo.gymandyang.login.ui.LoginScreen
import baeza.guillermo.gymandyang.login.ui.LoginViewModel
import baeza.guillermo.gymandyang.ui.model.Routes
import baeza.guillermo.gymandyang.ui.theme.ANDROID_FinalTheme
import baeza.guillermo.gymandyang.ui.theme.BackgroundGray
import baeza.guillermo.gymandyang.ui.theme.DarkPruple
import baeza.guillermo.gymandyang.ui.theme.MainPruple
import baeza.guillermo.gymandyang.R.drawable.gymyang_logo
import baeza.guillermo.gymandyang.ui.layoutComponents.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val drawerViewModel: DrawerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navCon = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            ANDROID_FinalTheme {
                NavHost(
                    navController = navCon,
                    startDestination = Routes.LoginScreen.route
                ) {
                    composable(route = Routes.LoginScreen.route) {
                        LoginScreen(navCon = navCon, scaffoldState = scaffoldState, loginViewModel = loginViewModel)
                    }
                    composable(route = Routes.RegisterScreen.route) {
                        Text(text = "Register")
                    }
                    composable(route = Routes.HomeScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            HomeScreen(navCon = navCon, scaffoldState = scaffoldState, homeViewModel = homeViewModel)
                        }
                    }
                    composable(route = Routes.ProfileScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            Text(text = "Profile")
                        }
                    }
                    composable(route = Routes.ClassesScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            Text(text = "Classes")
                        }
                    }
                    composable(route = Routes.PostsScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            Text(text = "Posts")
                        }
                    }
                    composable(route = Routes.DietsScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            Text(text = "Diets")
                        }
                    }
                    composable(route = Routes.SuggestionsScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            Text(text = "Suggestions")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomScaffold(
    drawerViewModel: DrawerViewModel,
    navCon: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {
    Scaffold (
        scaffoldState = scaffoldState,
        topBar = { TopBar(onIconClick = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }) },
        drawerContent = { CustomDrawer(drawerViewModel, navCon, scaffoldState) },
        content = { content() }
    )
}