package com.danilkha.conentfrientdsclient.core.domain

import android.util.Log

abstract class UseCase<P, R> {

    suspend operator fun invoke(params: P): Result<R> = kotlin.runCatching {
        execute(params)
    }.onFailure {
        Log.w("usecase", "Use case ${this::class.simpleName} failed with params: $params", it)
    }

    abstract suspend fun execute(params: P): R
}