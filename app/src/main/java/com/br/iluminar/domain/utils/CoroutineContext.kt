package com.br.iluminar.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineContext {

    fun main(): CoroutineDispatcher = Dispatchers.Main

    fun io(): CoroutineDispatcher = Dispatchers.IO

    fun default(): CoroutineDispatcher = Dispatchers.Default

    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class CoroutineContextImpl: CoroutineContext