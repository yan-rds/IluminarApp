package com.br.iluminar.data

import com.br.iluminar.data.dataSource.LoginDataSource
import com.br.iluminar.data.dataSource.TasksDataSource
import com.br.iluminar.data.dataSource.UserDataDataSource
import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.model.User
import com.br.iluminar.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

class RepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val userDataDataSource: UserDataDataSource,
    private val tasksDataSource: TasksDataSource
) : Repository {

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return loginDataSource.login(email, password)
    }

    override suspend fun signUp(
        email: String,
        password: String,
        user: User
    ): Resource<FirebaseUser> {
        return loginDataSource.signUp(email, password, user)
    }

    override suspend fun signOut() {
        loginDataSource.signOut()
    }

    override suspend fun getUserData(): Resource<UserDTO> {
        return userDataDataSource.getUserData()
    }

    override suspend fun getTaskList(): List<Task> {
        return tasksDataSource.getTaskList()
    }

    override suspend fun addTask(task: Task): Resource<Unit> {
        return tasksDataSource.addTask(task)
    }

}