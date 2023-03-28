package com.br.iluminar.data.dataSource

import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.utils.Resource

interface TasksDataSource {

    suspend fun getTaskList(): List<Task>

    suspend fun addTask(task: Task): Resource<Unit>
}