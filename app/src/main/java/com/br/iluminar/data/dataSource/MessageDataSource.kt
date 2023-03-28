package com.br.iluminar.data.dataSource

import com.br.iluminar.domain.model.Message
import com.br.iluminar.domain.utils.Resource

interface MessageDataSource {

    suspend fun addMessage(message: Message): Resource<Unit>
}