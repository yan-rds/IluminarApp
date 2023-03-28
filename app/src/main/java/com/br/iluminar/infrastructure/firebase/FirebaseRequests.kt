package com.br.iluminar.infrastructure.firebase

import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference

interface FirebaseRequests {

    val currentUser: FirebaseUser?

    val documentReference: DocumentReference?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun signUp(email: String, password: String, user: User): Resource<FirebaseUser>

    suspend fun signOut()

    suspend fun getUserData(): Resource<UserDTO>

    suspend fun getTasksList(): List<Task>

    suspend fun addTask(task: Task): Resource<Unit>

}