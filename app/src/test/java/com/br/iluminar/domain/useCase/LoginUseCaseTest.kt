package com.br.iluminar.domain.useCase

import com.br.iluminar.data.Repository
import com.br.iluminar.domain.utils.Resource
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals


class LoginUseCaseTest {

    private val repository: Repository = mockk()
    private val firebaseUser: FirebaseUser = mockk()
    private lateinit var loginUseCaseImpl: LoginUseCaseImpl

    @Before
    fun setUp() {
        loginUseCaseImpl = LoginUseCaseImpl(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `invoke - should call repository`() = runTest {
        val email = "yan@yan.com"
        val password = "112233"

        val resource: Resource<FirebaseUser> = Resource.Success(firebaseUser)
        coEvery {
            repository.login(email, password)
        } returns resource

        //act
        loginUseCaseImpl(email, password)

        //assert
        coVerify(exactly = 1) {
            repository.login(withArg { assertEquals(this.actual, email) }, password)
        }
    }
}