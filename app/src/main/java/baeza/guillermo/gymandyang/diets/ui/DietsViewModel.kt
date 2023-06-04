package baeza.guillermo.gymandyang.diets.ui

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.diets.data.dto.DietsDTO
import baeza.guillermo.gymandyang.diets.domain.DietsUseCase
import baeza.guillermo.gymandyang.ui.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DietsViewModel @Inject constructor(
    private val dietsUseCase: DietsUseCase,
    private val userPreference: UserPreferenceService
) : ViewModel() {
    private var _token = ""
    private var _email = ""

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _completed = MutableLiveData<Boolean>()
    val completed: LiveData<Boolean> = _completed

    private val _age = MutableLiveData<String>()
    val age: LiveData<String> = _age

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String> = _weight

    private val _height = MutableLiveData<String>()
    val height: LiveData<String> = _height

    private val _objective = MutableLiveData<String>()
    val objective: LiveData<String> = _objective

    private val _allergens = MutableLiveData<String>()
    val allergens: LiveData<String> = _allergens

    init {
        resetForm()
    }

    private fun resetForm() {
        _age.value = ""
        _gender.value = ""
        _weight.value = ""
        _height.value = ""
        _objective.value = ""
        _allergens.value = ""
    }

    fun initUser() {
        var user: User? = User("","","","","", listOf(), "")
        _loading.value = true
        viewModelScope.launch {
            user = userPreference.getUser("user")
            _loading.value = false
        }
        _email = user!!.email
        _token = user!!.token
    }

    fun setAge(value: String) { _age.value = value }

    fun setGender(value: String) { _gender.value = value }

    fun onFieldChange(weight: String, height: String, objective: String, allergens: String) {
        _weight.value = weight
        _height.value = height
        _objective.value = objective
        _allergens.value = allergens
    }

    fun setCompleted(value: Boolean) { _completed.value = value }

    fun onBtnClick(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        if (_age.value!!.isEmpty() || _gender.value!!.isEmpty() || _weight.value!!.isEmpty() || _height.value!!.isEmpty()) {
            launchSnackbar(scope, scaffoldState, "Complete compulsory fields (marked with *)")
        } else {
            doCreate(scope, scaffoldState)
        }
    }

    fun doCreate(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        _loading.value = true
        viewModelScope.launch {
            val result = dietsUseCase(
                DietsDTO(
                    client = _email,
                    age = _age.value!!,
                    gender = _gender.value!!,
                    weight = _weight.value!!,
                    height = _height.value!!,
                    objective = _objective.value!!,
                    allergens = _allergens.value!!
                ),
                _token
            )
            if (result == "ok") {
                Log.i("Gym", "Diet create completed")

                setCompleted(true)
                resetForm()
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
}