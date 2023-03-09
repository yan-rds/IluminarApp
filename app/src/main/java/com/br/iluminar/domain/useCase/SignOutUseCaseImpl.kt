package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository

class SignOutUseCaseImpl(private val repository: Repository): SignOutUseCase {
    override suspend fun invoke() {
        repository.signOut()
    }
}