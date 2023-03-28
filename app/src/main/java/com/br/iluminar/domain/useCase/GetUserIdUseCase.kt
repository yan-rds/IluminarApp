package com.br.iluminar.domain.useCase

import com.br.iluminar.domain.utils.Resource

interface GetUserIdUseCase {

    suspend operator fun invoke(): Resource<String?>
}