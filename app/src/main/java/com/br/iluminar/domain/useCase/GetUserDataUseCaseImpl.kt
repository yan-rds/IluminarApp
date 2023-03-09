package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.domain.dto.UserDTO

class GetUserDataUseCaseImpl(private val repository: Repository): GetUserDataUseCase {
    override suspend fun invoke(): Resource<UserDTO> {
        return repository.getUserData()
    }
}