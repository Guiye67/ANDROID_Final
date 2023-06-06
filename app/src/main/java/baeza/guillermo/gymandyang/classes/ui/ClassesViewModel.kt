package baeza.guillermo.gymandyang.classes.ui

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.gymandyang.classes.data.dto.ClassesDTO
import baeza.guillermo.gymandyang.classes.domain.GetClassesUseCase
import baeza.guillermo.gymandyang.classes.domain.UpdateUserUseCase
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.ui.models.GymClass
import baeza.guillermo.gymandyang.ui.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassesViewModel @Inject constructor(
    private val getClassesUseCase: GetClassesUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val userPreference: UserPreferenceService
) : ViewModel() {
    private var _user = User("", "", "", "", "", listOf(), "")

    private val _userClasses = MutableLiveData<List<String>>()
    val userClasses: LiveData<List<String>> = _userClasses

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _classes = MutableLiveData<List<GymClass>>()
    val classes: LiveData<List<GymClass>> = _classes

    fun initUser() {
        _loading.value = true
        viewModelScope.launch {
            _user = userPreference.getUser("user")!!
            _loading.value = false
        }
        _userClasses.value = _user.classes
    }

    fun getClasses() {
        _loading.value = true
        viewModelScope.launch {
            _classes.value = getClassesUseCase(_user.token)
            Log.i("GYM", "posts: ${_classes.value}")
            _loading.value = false
        }
    }

    fun signUp(scope: CoroutineScope, scaffoldState: ScaffoldState, className: String) {
        val newList = ArrayList(_userClasses.value!!)
        newList.add(className)

        updateUser(newList, scope, scaffoldState)
    }

    fun singOut(scope: CoroutineScope, scaffoldState: ScaffoldState, className: String) {
        val newList = ArrayList(_userClasses.value!!)
        newList.remove(className)

        updateUser(newList, scope, scaffoldState)
    }

    private fun updateUser(newList: ArrayList<String>, scope: CoroutineScope, scaffoldState: ScaffoldState) {
        _loading.value = true
        viewModelScope.launch {
            val result = updateUserUseCase(
                ClassesDTO(_user.email, newList),
                _user._id,
                _user.token
            )

            if (result != "ok") {
                launchSnackbar(scope, scaffoldState, result)
            }
            initUser()
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
}