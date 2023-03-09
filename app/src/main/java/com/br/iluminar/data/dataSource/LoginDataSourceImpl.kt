package com.br.iluminar.data.dataSource

import com.br.iluminar.infrastructure.firebase.FirebaseRequests
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.model.User
import com.google.firebase.auth.FirebaseUser


class LoginDataSourceImpl(private val firebaseRequests: FirebaseRequests) : LoginDataSource {

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return firebaseRequests.login(email, password)
    }

    override suspend fun getUserData(): Resource<UserDTO> {
        return firebaseRequests.getUserData()
    }

    override suspend fun signUp(
        email: String,
        password: String,
        user: User
    ): Resource<FirebaseUser> {
        return firebaseRequests.signUp(email, password, user)
    }

    override suspend fun signOut() {
        firebaseRequests.signOut()
    }
}