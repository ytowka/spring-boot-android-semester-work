package com.danilkha.conentfrientdsclient.features.users.ui.info

import androidx.lifecycle.ViewModel
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetMeUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetUserByIdUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UserInfoViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val userId: String,
) : ViewModel(){

    val _uiState = MutableStateFlow(UserInfoState())
    val uiState = _uiState.asStateFlow()
}