package com.br.iluminar.domain.useCase

import com.br.iluminar.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface LoginUseCase {

    suspend operator fun invoke(email:String, password:String): Resource<FirebaseUser>

}