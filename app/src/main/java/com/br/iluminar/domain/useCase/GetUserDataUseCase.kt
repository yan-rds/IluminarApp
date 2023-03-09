package com.br.iluminar.domain.useCase

import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.dto.UserDTO

interface GetUserDataUseCase {

    suspend operator fun invoke(): Resource<UserDTO>
}