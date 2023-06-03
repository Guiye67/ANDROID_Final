package baeza.guillermo.gymandyang.login.ui

import android.util.Log
import android.util.Patterns
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.login.domain.LoginUseCase
import baeza.guillermo.gymandyang.ui.models.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _validEmail = MutableLiveData<Boolean>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun onFieldChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _validEmail.value = isValidEmail(email)
    }

    fun onBtnClick(navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        if (_validEmail.value != null && _validEmail.value!!) {
            doLogin(navCon, scope, scaffoldState)
        }
        else {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Invalid Email",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    private fun doLogin(navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        _loading.value = true
        viewModelScope.launch {
            val result = loginUseCase(_email.value!!, _password.value!!)
            if(result._id != "-1") {
                Log.i("Gym", "Login completed")

                navCon.popBackStack()
                navCon.navigate(Routes.HomeScreen.route)
                onFieldChange("", "")
            } else {
                Log.i("Gym", "Error: ${result.email}")
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Error: ${result.email}",
                        duration = SnackbarDuration.Short
                    )
                }
            }
            _loading.value = false
        }
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}