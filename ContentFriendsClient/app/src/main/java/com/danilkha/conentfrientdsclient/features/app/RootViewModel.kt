package com.danilkha.conentfrientdsclient.features.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetMeUseCase
import com.danilkha.conentfrientdsclient.features.auth.domain.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.*
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RootViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val getMeUseCase: GetMeUseCase
) : ViewModel(){

    val isLoggedIn = isLoggedInUseCase()
        .map {
            it.getOrDefault(false)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val currentUser = flow {
        getMeUseCase().onSuccess {
            emit(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
}