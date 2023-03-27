package com.br.iluminar.data.dataSource

import com.br.iluminar.domain.model.User
import com.br.iluminar.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface LoginDataSource {

   suspend fun login(email: String, password: String): Resource<FirebaseUser>
   suspend fun signUp(email: String, password: String, user: User): Resource<FirebaseUser>
   suspend fun signOut()

}