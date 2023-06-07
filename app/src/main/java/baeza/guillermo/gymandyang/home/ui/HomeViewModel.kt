package baeza.guillermo.gymandyang.home.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import baeza.guillermo.gymandyang.home.domain.HomeUseCase
import baeza.guillermo.gymandyang.ui.models.GymClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _classes = MutableLiveData<List<GymClass>>()
    val classes: LiveData<List<GymClass>> = _classes

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _todaysClasses = MutableLiveData<List<GymClass>>()
    val todaysClasses: LiveData<List<GymClass>> = _todaysClasses

    init {
        _classes.value = listOf()
    }

    fun getClasses() {
        _loading.value = true
        viewModelScope.launch {
            _classes.value = homeUseCase()
            Log.i("GYM", "posts: ${_classes.value}")
            sortClasses()
            _loading.value = false
        }
    }

    private fun sortClasses() {
        val newList = ArrayList<GymClass>()
        _classes.value!!.forEach {
            if (it.days.contains(selectedDate.value!!.dayOfWeek.toString().lowercase().capitalize().substring(0, 3)))
                newList.add(it)
        }

        _todaysClasses.value = newList.sortedBy { it.hour }
    }

    fun onDateSelected(date: LocalDate) {
        _selectedDate.value = date
        sortClasses()
    }
}