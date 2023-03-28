package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.model.Task

class GetTaskListUseCaseImpl(private val repository: Repository): GetTaskListUseCase {

    override suspend fun invoke(): List<Task> {
        return repository.getTaskList()
    }
}