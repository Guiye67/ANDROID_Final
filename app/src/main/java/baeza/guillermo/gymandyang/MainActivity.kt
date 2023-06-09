package baeza.guillermo.gymandyang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import baeza.guillermo.gymandyang.classes.ui.ClassesScreen
import baeza.guillermo.gymandyang.classes.ui.ClassesViewModel
import baeza.guillermo.gymandyang.diets.ui.DietsScreen
import baeza.guillermo.gymandyang.diets.ui.DietsViewModel
import baeza.guillermo.gymandyang.home.ui.HomeScreen
import baeza.guillermo.gymandyang.home.ui.HomeViewModel
import baeza.guillermo.gymandyang.login.ui.LoginScreen
import baeza.guillermo.gymandyang.login.ui.LoginViewModel
import baeza.guillermo.gymandyang.posts.ui.PostsScreen
import baeza.guillermo.gymandyang.posts.ui.PostsViewModel
import baeza.guillermo.gymandyang.profile.ui.ProfileScreen
import baeza.guillermo.gymandyang.profile.ui.ProfileViewModel
import baeza.guillermo.gymandyang.register.ui.RegisterScreen
import baeza.guillermo.gymandyang.register.ui.RegisterViewModel
import baeza.guillermo.gymandyang.suggestions.ui.SuggestionsScreen
import baeza.guillermo.gymandyang.suggestions.ui.SuggestionsViewModel
import baeza.guillermo.gymandyang.ui.models.Routes
import baeza.guillermo.gymandyang.ui.theme.ANDROID_FinalTheme
import baeza.guillermo.gymandyang.ui.splashScreen.SplashScreen
import baeza.guillermo.gymandyang.ui.layoutComponents.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val drawerViewModel: DrawerViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val dietsViewModel: DietsViewModel by viewModels()
    private val suggestionsViewModel: SuggestionsViewModel by viewModels()
    private val postsViewModel: PostsViewModel by viewModels()
    private val classesViewModel: ClassesViewModel by viewModels()

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
                        RegisterScreen(navCon = navCon, scaffoldState = scaffoldState, registerViewModel = registerViewModel)
                    }
                    composable(route = Routes.HomeScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            HomeScreen(navCon = navCon, scaffoldState = scaffoldState, homeViewModel = homeViewModel)
                        }
                    }
                    composable(route = Routes.ProfileScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            ProfileScreen(navCon = navCon, scaffoldState = scaffoldState, profileViewModel = profileViewModel)
                        }
                    }
                    composable(route = Routes.ClassesScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            ClassesScreen(scaffoldState = scaffoldState, classesViewModel = classesViewModel)
                        }
                    }
                    composable(route = Routes.PostsScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            PostsScreen(scaffoldState = scaffoldState, postsViewModel = postsViewModel)
                        }
                    }
                    composable(route = Routes.DietsScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            DietsScreen(scaffoldState = scaffoldState, dietsViewModel = dietsViewModel)
                        }
                    }
                    composable(route = Routes.SuggestionsScreen.route) {
                        CustomScaffold(drawerViewModel, navCon, scaffoldState, scope) {
                            SuggestionsScreen(scaffoldState = scaffoldState, suggestionsViewModel = suggestionsViewModel)
                        }
                    }
                    composable(route = Routes.SplashScreen.route) {
                        SplashScreen(navCon = navCon)
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