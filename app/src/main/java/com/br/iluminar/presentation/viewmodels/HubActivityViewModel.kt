package com.br.iluminar.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.iluminar.domain.dto.UserDTO
import com.br.iluminar.domain.useCase.GetUserDataUseCase
import com.br.iluminar.domain.useCase.SignOutUseCase
import com.br.iluminar.domain.utils.CoroutineContext
import com.br.iluminar.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HubActivityViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val coroutineContext: CoroutineContext
) :
    ViewModel() {

    private val _userDto = MutableLiveData<UserDTO>()
    val userDTO: LiveData<UserDTO> = _userDto

    private val _dataFlow = MutableLiveData<Resource<UserDTO>?>()
    val dataFlow: LiveData<Resource<UserDTO>?> = _dataFlow

    fun getUserData() = viewModelScope.launch(coroutineContext.main()) {
        _dataFlow.value = Resource.Loading
        val result = getUserDataUseCase.invoke()
        Log.e("result", "$result")
        _dataFlow.value = result
    }

    fun signOut() = viewModelScope.launch {
        signOutUseCase.invoke()
    }
}