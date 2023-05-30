package baeza.guillermo.gymandyang.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.ui.model.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _validEmail = MutableLiveData<Boolean>()
    val validEmail: LiveData<Boolean> = _validEmail

    private val _view = MutableLiveData<Int>()
    val view: LiveData<Int> = _view

    private val _incorrectData = MutableLiveData<Boolean>()
    val incorrectData: LiveData<Boolean> = _incorrectData

    fun onFieldChange(email: String, password: String) {
        _email.value = email
        _password.value = password
        _validEmail.value = isValidEmail(email)
    }

    fun onLogin(navCon: NavHostController) {
//        _view.value = 2
        navCon.navigate(Routes.HomeScreen.route)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}