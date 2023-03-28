package com.br.iluminar.domain.useCase

import com.br.iluminar.domain.model.Task

interface GetTaskListUseCase {

    suspend operator fun invoke(): List<Task>
}