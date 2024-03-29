package com.br.iluminar.data.dataSource

import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.infrastructure.firebase.FirebaseRequests

class TasksDataSourceImpl(private val firebaseRequests: FirebaseRequests) : TasksDataSource {
    override suspend fun getTaskList(): List<Task> {
        return firebaseRequests.getTasksList()
    }

    override suspend fun addTask(task: Task): Resource<Unit> {
        return firebaseRequests.addTask(task)
    }
}