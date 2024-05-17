package com.danilkha.conentfrientdsclient.core.domain

import android.util.Log

abstract class SimpleUseCase<R> {

    suspend operator fun invoke(): Result<R> = kotlin.runCatching {
        execute()
    }.onFailure {
        Log.w("usecase", "Use case ${this::class.simpleName} failed", it)
    }

    abstract suspend fun execute(): R
}