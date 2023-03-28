package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.utils.Resource

class GetUserIdUseCaseImpl(private val repository: Repository): GetUserIdUseCase {
    override suspend fun invoke(): Resource<String?> {
        return repository.getUserId()
    }
}