package com.br.iluminar.di

import com.br.iluminar.data.Repository
import com.br.iluminar.data.RepositoryImpl
import com.br.iluminar.data.dataSource.*
import com.br.iluminar.infrastructure.firebase.FirebaseRequests
import com.br.iluminar.infrastructure.firebase.FirebaseRequestsImpl
import com.br.iluminar.domain.useCase.*
import com.br.iluminar.domain.utils.CoroutineContext
import com.br.iluminar.domain.utils.CoroutineContextImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideRepository(
        requestLoginDataSource: LoginDataSource,
        userDataDataSource: UserDataDataSource,
        tasksDataSource: TasksDataSource
    ): Repository =
        RepositoryImpl(requestLoginDataSource, userDataDataSource, tasksDataSource)

    @Singleton
    @Provides
    fun provideLoginUseCase(repository: Repository): LoginUseCase = LoginUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideSignOutUseCase(repository: Repository): SignOutUseCase =
        SignOutUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideSignUpUseCase(repository: Repository): SignUpUseCase = SignUpUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideGetUserDataUseCase(repository: Repository): GetUserDataUseCase =
        GetUserDataUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideGetTaskListUseCase(repository: Repository): GetTaskListUseCase =
        GetTaskListUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideLoginDataSource(firebaseRequests: FirebaseRequests): LoginDataSource =
        LoginDataSourceImpl(firebaseRequests)

    @Singleton
    @Provides
    fun provideUserDataDataSource(firebaseRequests: FirebaseRequests): UserDataDataSource =
        UserDataDataSourceImpl(firebaseRequests)

    @Singleton
    @Provides
    fun provideTasksDataSource(firebaseRequests: FirebaseRequests): TasksDataSource =
        TasksDataSourceImpl(firebaseRequests)

    @Singleton
    @Provides
    fun provideFirebaseRequests(coroutineContext: CoroutineContext): FirebaseRequests =
        FirebaseRequestsImpl(coroutineContext)


    @Singleton
    @Provides
    fun provideCoroutineContext(): CoroutineContext =
        CoroutineContextImpl()

}