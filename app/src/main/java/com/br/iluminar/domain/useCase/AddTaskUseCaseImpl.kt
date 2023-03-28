package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.utils.Resource

class AddTaskUseCaseImpl( private val repository: Repository): AddTaskUseCase {

    override suspend fun invoke(task: Task): Resource<Unit> {
        return repository.addTask(task)
    }
}