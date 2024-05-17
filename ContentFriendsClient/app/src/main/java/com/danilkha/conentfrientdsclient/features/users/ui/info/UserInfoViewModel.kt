package com.danilkha.conentfrientdsclient.features.users.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danilkha.conentfrientdsclient.core.ui.PagingResponse
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewDto
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.GetReviewsByContentUseCase
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.GetReviewsByUserUseCase
import com.danilkha.conentfrientdsclient.features.review.ui.toReviewModel
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetMatchScoreUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetMeUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.GetUserByIdUseCase
import com.danilkha.conentfrientdsclient.features.users.domain.usecase.UpdateUserUseCase
import com.danilkha.conentfrientdsclient.features.users.ui.toUserModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.util.*

@KoinViewModel
class UserInfoViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getMatchScoreUseCase: GetMatchScoreUseCase,
    private val reviewsByUserUseCase: GetReviewsByUserUseCase,
    private val userId: String,
) : ViewModel(){

    val _uiState = MutableStateFlow(UserInfoState())
    val uiState = _uiState.asStateFlow()

    init {
        getNextPage()
        viewModelScope.launch {
            getUserByIdUseCase(userId).onSuccess { result ->
                _uiState.update {
                    it.copy(userModel = result.toUserModel())
                }
            }
        }
        viewModelScope.launch {
            getMatchScoreUseCase(UUID.fromString(userId)).onSuccess { result ->
                _uiState.update {
                    it.copy(matchScore = result)
                }
            }
        }
    }

    fun getNextPage(){
        viewModelScope.launch {
            uiState.value.reviewListState.loadNext {
                val params = GetReviewsByUserUseCase.Params(UUID.fromString(userId), it)
                val result = reviewsByUserUseCase(params).getOrThrow()
                PagingResponse(
                    data = result.reviews.map(ReviewDto::toReviewModel),
                    page = result.page,
                    hasNextPage = result.hasNextPage
                )
            }.collectLatest { pagingData ->
                _uiState.update { it.copy(reviewListState = pagingData) }
            }
        }
    }
}