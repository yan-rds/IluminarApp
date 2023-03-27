package com.br.iluminar.data.dataSource

import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.utils.Resource

interface UserDataDataSource {

    suspend fun getUserData(): Resource<UserDTO>
}