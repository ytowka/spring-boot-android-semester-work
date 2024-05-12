package com.danilkha.conentfrientdsclient.features.review.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.core.ui.PagingResponse
import com.danilkha.conentfrientdsclient.features.content.domain.dto.ContentDto
import com.danilkha.conentfrientdsclient.features.content.ui.toContentModel
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.GetReviewsByContentUseCase
import com.danilkha.conentfrientdsclient.features.review.ui.toReviewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ReviewListViewModel(
    val reviewsByContentUseCase: GetReviewsByContentUseCase,
    val contentId: Long,
) : ViewModel(){

    val _uiState = MutableStateFlow(ReviewListState())
    val uiState = _uiState.asStateFlow()

    init {
        getNextPage()
    }

    fun getNextPage(){
        viewModelScope.launch {
            uiState.value.listState.loadNext {
                val result = reviewsByContentUseCase(contentId, it).getOrThrow()
                PagingResponse(
                    data = result.reviews.map(ReviewDto::toReviewModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                _uiState.update { it.copy(listState = pagingData) }
            }
        }
    }
}