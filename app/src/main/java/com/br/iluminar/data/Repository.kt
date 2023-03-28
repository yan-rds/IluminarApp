package com.br.iluminar.data

import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.model.User
import com.br.iluminar.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface Repository {

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun signUp(email: String, password: String, user: User): Resource<FirebaseUser>

    suspend fun signOut()

    suspend fun getUserData(): Resource<UserDTO>

    suspend fun getTaskList(): List<Task>

    suspend fun addTask(task: Task): Resource<Unit>
}