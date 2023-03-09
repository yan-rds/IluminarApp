package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.model.User
import com.google.firebase.auth.FirebaseUser

class SignUpUseCaseImpl(private val repository: Repository) : SignUpUseCase {
    override suspend fun invoke(
        email: String,
        password: String,
        user: User
    ): Resource<FirebaseUser> {
        return repository.signUp(email, password, user)
    }

}