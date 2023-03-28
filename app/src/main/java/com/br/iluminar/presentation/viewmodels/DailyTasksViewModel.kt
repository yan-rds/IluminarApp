package com.br.iluminar.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.useCase.GetTaskListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyTasksViewModel @Inject constructor(
    private val getTaskListUseCase: GetTaskListUseCase
): ViewModel() {

    private val _taskListFlow = MutableLiveData<List<Task>>()
    val taskListFlow: LiveData<List<Task>> = _taskListFlow

    fun getTaskList() = viewModelScope.launch {
        _taskListFlow.value = getTaskListUseCase.invoke()
    }
}