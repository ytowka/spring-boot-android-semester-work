package com.danilkha.conentfrientdsclient.core.domain

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class SimpleFlowUseCase<R> {

    operator fun invoke(): Flow<Result<R>> = execute()
        .map {
            Result.success(it)
        }.catch {
            Log.w("usecase", "Use case ${this::class.simpleName} failing", it)
            emit(Result.failure(it))
        }

    abstract fun execute(): Flow<R>
}