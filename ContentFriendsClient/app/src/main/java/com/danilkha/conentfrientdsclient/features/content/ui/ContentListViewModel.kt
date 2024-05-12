package com.danilkha.conentfrientdsclient.features.content.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.core.ui.PagingResponse
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.domain.usecase.GetAllContentUseCase
import com.danilkha.conentfrientdsclient.features.content.domain.usecase.GetRecommendedContentUseCase
import com.danilkha.conentfrientdsclient.features.content.domain.usecase.SearchContentUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.dto.UserDto
import com.danilkha.conentfrientdsclient.features.users.ui.toUserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ContentListViewModel(
    private val getAllContentUseCase: GetAllContentUseCase,
    private val searchContentUseCase: SearchContentUseCase,
    private val topicId: Long
) : ViewModel() {


    val _uiState = MutableStateFlow(ContentListState())
    val uiState = _uiState.asStateFlow()

    init{
        getNextPage()
        viewModelScope.launch {
            uiState.map { it.searchQuery }
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest {
                    val content = searchContentUseCase(topicId, it).getOrElse { emptyList() }
                    _uiState.update { it.copy(
                        searchList = content.map { it.toContentModel() }
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
            uiState.value.pagerState.loadNext {
                val result = getAllContentUseCase(topicId, it).getOrThrow()
                PagingResponse(
                    data = result.content.map(ContentDto::toContentModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                _uiState.update { it.copy(pagerState = pagingData) }
            }
        }
    }
}