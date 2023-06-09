package baeza.guillermo.gymandyang.profile.ui

import android.util.Log
import android.util.Patterns
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.profile.data.dto.ProfileDTO
import baeza.guillermo.gymandyang.profile.domain.ProfileUseCase
import baeza.guillermo.gymandyang.ui.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val userPreference: UserPreferenceService
) : ViewModel() {
    private var _id = ""
    private var _token = ""

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

    init {
        _changePassword.value = false
        _validEmail.value = false
        _validPassword.value = false
        _passwordsMatch.value = false
    }

    fun initUser() {
        var user: User? = User("","","","","", listOf(), "")
        _loading.value = true
        viewModelScope.launch {
            user = userPreference.getUser("user")
            _loading.value = false
        }
        _id = user!!._id
        _email.value = user!!.email
        _name.value = user!!.name
        _surname.value = user!!.surname
        _payment.value = user!!.payment
        _classes.value = user!!.classes
        _token = user!!.token
    }

    fun setUpdating(value: Boolean) { _updating.value = value }

    fun setShowPaymentDialog(value: Boolean) { _showPaymentDialog.value = value }

    fun setChangePassword(value: Boolean) { _changePassword.value = value }

    fun addMonths(num: Long, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        var userPayment: LocalDate = LocalDate.now()
        if (_payment.value != "0") userPayment = LocalDate.parse(_payment.value)

        userPayment = userPayment.plusMonths(num)

        _payment.value = userPayment.toString()

        doUpdate(scope, scaffoldState)
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

    fun onBtnClick(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        if (!_validEmail.value!!) {
            launchSnackbar(scope, scaffoldState, "Invalid Email")
        }
        else if (_changePassword.value!! &&  !_validPassword.value!!) {
            launchSnackbar(scope, scaffoldState, "Invalid Password (min length: 8)")
        }
        else if (_changePassword.value!! && !_passwordsMatch.value!!) {
            launchSnackbar(scope, scaffoldState, "Passwords don't match")
        }
        else {
            doUpdate(scope, scaffoldState)
        }
    }

    private fun doUpdate(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        _loading.value = true
        viewModelScope.launch {
            val result = profileUseCase(
                ProfileDTO(
                    email = _email.value!!,
                    name = _name.value!!,
                    surname = _surname.value!!,
                    password = if (_changePassword.value!!) _newPassword.value!! else null,
                    payment = _payment.value!!,
                    classes = _classes.value!!
                ),
                _id,
                _token
            )
            if (result == "ok") {
                Log.i("Gym", "Update completed")

                if (_changePassword.value!!) {
                    launchSnackbar(scope, scaffoldState, "Password updated successfuly")
                }

                _changePassword.value = false
                _updating.value = false
                initUser()
            } else {
                Log.i("Gym", "Error: $result")
                launchSnackbar(scope, scaffoldState, "Error: $result")
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