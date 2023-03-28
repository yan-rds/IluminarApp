package com.br.iluminar.data.dataSource


import com.br.iluminar.domain.model.Message
import com.br.iluminar.domain.utils.Resource
import com.br.iluminar.infrastructure.firebase.FirebaseRequests

class MessageDataSourceImpl(private val firebaseRequests: FirebaseRequests) : MessageDataSource {

    override suspend fun addMessage(message: Message): Resource<Unit> {
        return firebaseRequests.addMessage(message)
    }
}