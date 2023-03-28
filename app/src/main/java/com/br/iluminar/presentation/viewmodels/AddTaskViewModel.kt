package com.br.iluminar.presentation.viewmodels

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.useCase.AddTaskUseCase
import com.br.iluminar.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
): ViewModel() {

    private val _resource = MutableLiveData<Resource<Unit>?>(null)
    val resource: LiveData<Resource<Unit>?> = _resource

    private val _startTime = MutableLiveData<LocalTime>()
    val startTime: LiveData<LocalTime> = _startTime

    private val _endTime = MutableLiveData<LocalTime>()
    val endTime: LiveData<LocalTime> = _endTime

    private val _selectedDate = MutableLiveData<Calendar>()
    val selectedDate: LiveData<Calendar> = _selectedDate

    fun addTask(task: Task) = viewModelScope.launch {
        _resource.value = Resource.Loading
        val result = addTaskUseCase.invoke(task)
        _resource.value = result
    }

    fun showDatePicker(context: Context) {
        val now = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                _selectedDate.value = selectedDate
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    fun showStartTimePicker(context: Context) {
        val currentTime = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val selectedTime: LocalTime = LocalTime.of(hourOfDay, minute)
                _startTime.value = selectedTime
            },
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }

    fun showEndTimePicker(context: Context) {
        val currentTime = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val selectedTime: LocalTime = LocalTime.of(hourOfDay, minute)
                _endTime.value = selectedTime
            },
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }
}
