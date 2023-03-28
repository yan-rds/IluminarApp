package com.br.iluminar.domain.useCase

import com.br.iluminar.domain.model.Message
import com.br.iluminar.domain.utils.Resource

interface AddMessageUseCase {

    suspend operator fun invoke(message: Message): Resource<Unit>
}