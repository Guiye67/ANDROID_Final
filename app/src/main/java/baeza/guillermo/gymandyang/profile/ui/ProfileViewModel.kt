package baeza.guillermo.gymandyang.profile.ui

import android.util.Log
import android.util.Patterns
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.register.data.dto.RegisterDTO
import baeza.guillermo.gymandyang.ui.models.Routes
import baeza.guillermo.gymandyang.ui.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    //private val registerUseCase: RegisterUseCase
    private val userPreference: UserPreferenceService
) : ViewModel() {
    private val _id = MutableLiveData<String>()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String> = _surname

    private val _newPassword = MutableLiveData<String>()
    val newPassword: LiveData<String> = _newPassword

    private val _newPasswordRepeat = MutableLiveData<String>()
    val newPasswordRepeat: LiveData<String> = _newPasswordRepeat

    private val _payment = MutableLiveData<String>()
    val payment: LiveData<String> = _payment

    private val _classes = MutableLiveData<List<String>>()
    val classes: LiveData<List<String>> = _classes

    private val _token = MutableLiveData<String>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _updating = MutableLiveData<Boolean>()
    val updating: LiveData<Boolean> = _updating

    private val _showPaymentDialog = MutableLiveData<Boolean>()
    val showPaymentDialog: LiveData<Boolean> = _showPaymentDialog

    private val _changePassword = MutableLiveData<Boolean>()
    val changePassword: LiveData<Boolean> = _changePassword

    private val _validEmail = MutableLiveData<Boolean>()

    private val _validPassword = MutableLiveData<Boolean>()

    private val _passwordsMatch = MutableLiveData<Boolean>()

    fun initUser() {
        var user: User? = User("","","","","", listOf(), "")
        _loading.value = true
        viewModelScope.launch {
            user = userPreference.getUser("user")
            _loading.value = false
        }
        _id.value = user!!._id
        _email.value = user!!.email
        _name.value = user!!.name
        _surname.value = user!!.surname
        _payment.value = user!!.payment
        _classes.value = user!!.classes
        _token.value = user!!.token
    }

    fun setUpdating(value: Boolean) { _updating.value = value }

    fun setShowPaymentDialog(value: Boolean) { _showPaymentDialog.value = value }

    fun setChangePassword(value: Boolean) { _changePassword.value = value }

    fun addMonths(num: Long) {
        var userPayment: LocalDate = LocalDate.now()
        if (_payment.value != "0") userPayment = LocalDate.parse(_payment.value)

        Log.i("GYM", "months: $userPayment")

        userPayment = userPayment.plusMonths(num)

        Log.i("GYM", "months: $userPayment")


    }

    fun onFieldChange(name: String, surname: String, email: String, newPassword: String, newPasswordRepeat: String) {
        _name.value = name
        _surname.value = surname
        _email.value = email
        _newPassword.value = newPassword
        _newPasswordRepeat.value = newPasswordRepeat
        _validEmail.value = isValidEmail(email)
        _validPassword.value = isValidPassword(newPassword)
        _passwordsMatch.value = newPassword == newPasswordRepeat
    }

    fun onCancelUpdating() {
        initUser()
        setUpdating(false)
    }

    fun onBtnClick(navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        if (!_validEmail.value!!) {
            launchSnackbar(scope, scaffoldState, "Invalid Email")
        }
        else if (_changePassword.value!! && (_validPassword.value == null || !_validPassword.value!!)) {
            launchSnackbar(scope, scaffoldState, "Invalid Password (min length: 8)")
        }
        else if (_changePassword.value!! && (_passwordsMatch.value == null || !_passwordsMatch.value!!)) {
            launchSnackbar(scope, scaffoldState, "Passwords don't match")
        }
        else {
            doUpdate(navCon, scope, scaffoldState)
        }
    }

    fun doUpdate(navCon: NavHostController, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        _loading.value = true
        viewModelScope.launch {
//            val result = registerUseCase(
//                RegisterDTO(
//                    email = _email.value!!,
//                    name = _name.value!!,
//                    surname = _surname.value!!,
//                    password = _password.value!!
//                )
//            )
//            if (result._id != "-1") {
//                Log.i("Gym", "Register completed")
//
////                navCon.popBackStack()
////                navCon.navigate(Routes.HomeScreen.route)
//                navCon.navigate(Routes.HomeScreen.route) {popUpTo(navCon.graph.id) {inclusive = true} }
//                onFieldChange("", "", "", "", "")
//            } else {
//                Log.i("Gym", "Error: ${result.email}")
//                scope.launch {
//                    scaffoldState.snackbarHostState.showSnackbar(
//                        message = "Error: ${result.email}",
//                        duration = SnackbarDuration.Short
//                    )
//                }
//            }
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