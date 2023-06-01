package baeza.guillermo.gymandyang.ui.layoutComponents

import androidx.compose.material.ScaffoldState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.login.data.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.login.data.dto.UserDTO
import baeza.guillermo.gymandyang.ui.model.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val userPreference: UserPreferenceService
) : ViewModel() {
    private val _user = MutableLiveData<UserDTO>()
    val user: LiveData<UserDTO> = _user

    fun getUser() {
        viewModelScope.launch {
            _user.value = userPreference.getUser("user")
        }
    }

    fun doLogout(navCon: NavHostController, scaffoldState: ScaffoldState, scope: CoroutineScope) {
        scope.launch {
            scaffoldState.drawerState.close()
            userPreference.clearData()
            navCon.popBackStack(Routes.LoginScreen.route, inclusive = false)
        }
    }
}