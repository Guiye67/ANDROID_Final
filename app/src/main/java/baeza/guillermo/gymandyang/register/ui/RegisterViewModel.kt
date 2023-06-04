package baeza.guillermo.gymandyang.register.ui

import android.util.Log
import android.util.Patterns
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.register.data.dto.RegisterDTO
import baeza.guillermo.gymandyang.register.domain.RegisterUseCase
import baeza.guillermo.gymandyang.ui.models.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String> = _surname

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _passwordRepeat = MutableLiveData<String>()
    val passwordRepeat: LiveData<String> = _passwordRepeat

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _validEmail = MutableLiveData<Boolean>()

    private val _validPassword = MutableLiveData<Boolean>()

    private val _passwordsMatch = MutableLiveData<Boolean>()

    fun onFieldChange(email: String, name: String, surname: String, password: String, passwordRepeat: String) {
        _email.value = email
        _name.value = name
        _surname.value = surname
        _password.value = password
        _passwordRepeat.value = passwordRepeat
        _validEmail.value = isValidEmail(email)
        _validPassword.value = isValidPassword(password)
        _passwordsMatch.value = password == passwordRepeat
    }

    fun onBtnClick(navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        if (_validEmail.value == null || !_validEmail.value!!) {
            launchSnackbar(scope, scaffoldState, "Invalid Email")
        }
        else if (_validPassword.value == null || !_validPassword.value!!) {
            launchSnackbar(scope, scaffoldState, "Invalid Password (min length: 8)")
        }
        else if (_passwordsMatch.value == null || !_passwordsMatch.value!!) {
            launchSnackbar(scope, scaffoldState, "Passwords don't match")
        }
        else {
            doRegister(navCon, scope, scaffoldState)
        }
    }

    private fun doRegister(navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        _loading.value = true
        viewModelScope.launch {
            val result = registerUseCase(
                RegisterDTO(
                    email = _email.value!!,
                    name = _name.value!!,
                    surname = _surname.value!!,
                    password = _password.value!!
                )
            )
            if (result._id != "-1") {
                Log.i("Gym", "Register completed")

                navCon.navigate(Routes.HomeScreen.route) {popUpTo(navCon.graph.id) {inclusive = true} }
                onFieldChange("", "", "", "", "")
            } else {
                Log.i("Gym", "Error: ${result.email}")
                launchSnackbar(scope, scaffoldState, "Error: ${result.email}")
            }
            _loading.value = false
        }
    }

    private fun launchSnackbar(scope: CoroutineScope, scaffoldState: ScaffoldState, message: String) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length >= 8
}