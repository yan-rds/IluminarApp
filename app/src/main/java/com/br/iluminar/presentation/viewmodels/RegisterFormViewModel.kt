package com.br.iluminar.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.iluminar.domain.model.User
import com.br.iluminar.domain.useCase.SignUpUseCase
import com.br.iluminar.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFormViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private val _signUpFlow = MutableLiveData<Resource<FirebaseUser>?>(null)
    val signUpFlow: LiveData<Resource<FirebaseUser>?> = _signUpFlow

    fun signUp(email: String, password: String, user: User) = viewModelScope.launch {
        _signUpFlow.value = Resource.Loading
        val result = signUpUseCase.invoke(email, password, user)

        _signUpFlow.value = result
    }
}