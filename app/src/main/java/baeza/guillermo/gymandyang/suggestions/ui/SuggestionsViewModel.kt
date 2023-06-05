package baeza.guillermo.gymandyang.suggestions.ui

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.gymandyang.datastore.UserPreferenceService
import baeza.guillermo.gymandyang.suggestions.data.dto.SuggestionsDTO
import baeza.guillermo.gymandyang.suggestions.domain.SuggestionsUseCase
import baeza.guillermo.gymandyang.ui.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestionsViewModel @Inject constructor(
    private val suggestionsUseCase: SuggestionsUseCase,
    private val userPreference: UserPreferenceService
) : ViewModel() {
    private var _token = ""
    private var _email = ""

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _completed = MutableLiveData<Boolean>()
    val completed: LiveData<Boolean> = _completed

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    init {
        resetForm()
    }

    private fun resetForm() {
        _title.value = ""
        _description.value = ""
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

    fun onFieldChange(title: String, description: String) {
        _title.value = title
        _description.value = description
    }

    fun setCompleted(value: Boolean) { _completed.value = value }

    fun onBtnClick(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        if (_title.value!!.isEmpty() || _description.value!!.isEmpty()) {
            launchSnackbar(scope, scaffoldState, "Complete both fields please")
        } else {
            doCreate(scope, scaffoldState)
        }
    }

    private fun doCreate(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        _loading.value = true
        viewModelScope.launch {
            val result = suggestionsUseCase(
                SuggestionsDTO(
                    title = _title.value!!,
                    client = _email,
                    description = _description.value!!
                ),
                _token
            )
            if (result == "ok") {
                Log.i("Gym", "Suggestion create completed")

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