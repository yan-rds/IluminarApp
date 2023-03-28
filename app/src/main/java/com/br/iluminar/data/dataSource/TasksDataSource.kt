package com.br.iluminar.data.dataSource

import com.br.iluminar.domain.model.Task

interface TasksDataSource {

    suspend fun getTaskList(): List<Task>
}