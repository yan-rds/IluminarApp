package com.br.iluminar.domain.useCase

import com.br.iluminar.domain.model.Task
import com.br.iluminar.domain.utils.Resource

interface AddTaskUseCase {

    suspend operator fun invoke(task: Task): Resource<Unit>
}