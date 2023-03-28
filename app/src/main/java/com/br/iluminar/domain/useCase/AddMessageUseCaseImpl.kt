package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.model.Message
import com.br.iluminar.domain.utils.Resource

class AddMessageUseCaseImpl(private val repository: Repository): AddMessageUseCase {
    override suspend fun invoke(message: Message): Resource<Unit> {
        return repository.addMessage(message)
    }
}