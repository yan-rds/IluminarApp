package com.br.iluminar.domain.useCase

import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.model.User
import com.google.firebase.auth.FirebaseUser

interface SignUpUseCase {

    suspend operator fun invoke(email:String, password:String, user:User): Resource<FirebaseUser>
}