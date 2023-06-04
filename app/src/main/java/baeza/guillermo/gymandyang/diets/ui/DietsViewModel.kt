package baeza.guillermo.gymandyang.diets.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.ui.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DietsViewModel @Inject constructor(
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

    private val _weight = MutableLiveData<Int>()
    val weight: LiveData<Int> = _weight

    private val _height = MutableLiveData<Int>()
    val height: LiveData<Int> = _height

    private val _objective = MutableLiveData<String>()
    val objective: LiveData<String> = _objective

    private val _allergens = MutableLiveData<String>()
    val allergens: LiveData<String> = _allergens

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

    fun onFieldChange(weight: Int, height: Int, objective: String, allergens: String) {
        _weight.value = weight
        _height.value = height
        _objective.value = objective
        _allergens.value = allergens
    }

    fun setCompleted(value: Boolean) { _completed.value = value }
}