package com.danilkha.conentfrientdsclient.features.users.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.core.ui.PagingResponse
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.ui.toContentModel
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetUserListUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.SearchUserUseCase
import com.danilkha.conentfrientdsclient.features.users.ui.toUserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UserListViewModel(
    private val getUserListUseCase: GetUserListUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel(){


    private val _uiState = MutableStateFlow(UserListState())
    val uiState: StateFlow<UserListState> = _uiState

    init {
        viewModelScope.launch {
            getNextPage()
        }
        viewModelScope.launch {
            uiState.map { it.searchQuery }
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest {
                    val users = searchUserUseCase(it).getOrDefault(emptyList())
                    _uiState.update { it.copy(
                        searchUsers = users.map {
                            it.toUserModel()
                        }
                    ) }
                }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update {
            it.copy(searchQuery = query)
        }
    }

    fun getNextPage(){
        viewModelScope.launch {
            uiState.value.users.loadNext {
                val result = getUserListUseCase(it).getOrThrow()
                PagingResponse(
                    data = result.users.map(UserDto::toUserModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest {
                _uiState.update { state -> state.copy(users = it) }
            }
        }
    }
}