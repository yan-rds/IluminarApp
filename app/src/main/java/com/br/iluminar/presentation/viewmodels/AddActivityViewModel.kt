package com.br.iluminar.presentation.viewmodels

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime
import java.util.*

class AddTaskViewModel : ViewModel() {

    init {

    }

    private val _startTime = MutableLiveData<LocalTime>()
    val startTime: LiveData<LocalTime> = _startTime

    private val _endTime = MutableLiveData<LocalTime>()
    val endTime: LiveData<LocalTime> = _endTime

    private val _selectedDate = MutableLiveData<Calendar>()
    val selectedDate: LiveData<Calendar> = _selectedDate

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
