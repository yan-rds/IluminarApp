package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

class LoginUseCaseImpl(private val repository: Repository): LoginUseCase {

    override suspend fun invoke(email: String, password: String): Resource<FirebaseUser> {
        return repository.login(email, password)
    }

}