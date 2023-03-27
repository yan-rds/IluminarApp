package com.br.iluminar.data.dataSource

import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.infrastructure.firebase.FirebaseRequests

class UserDataDataSourceImpl (private val firebaseRequests: FirebaseRequests): UserDataDataSource {

    override suspend fun getUserData(): Resource<UserDTO> {
        return firebaseRequests.getUserData()
    }
}