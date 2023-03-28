package com.br.iluminar.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.iluminar.domain.model.Message
import com.br.iluminar.domain.useCase.AddMessageUseCase
import com.br.iluminar.domain.useCase.GetUserIdUseCase
import com.br.iluminar.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val addMessageUseCase: AddMessageUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
): ViewModel() {

    private val _messageResource = MutableLiveData<Resource<Unit>>()
    val messageResource: LiveData<Resource<Unit>> = _messageResource

    private val _userIdResource = MutableLiveData<Resource<String?>>()
    val userIdResource: LiveData<Resource<String?>> = _userIdResource

    fun addMessage(message:Message) = viewModelScope.launch {
        val result = addMessageUseCase.invoke(message)
        _messageResource.value = result
    }

    fun getUserId() = viewModelScope.launch {
        val result = getUserIdUseCase.invoke()
        _userIdResource.value = result
    }

}