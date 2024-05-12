package com.danilkha.conentfrientdsclient.features.review.ui.edit

import androidx.lifecycle.ViewModel
import com.danilkha.conentfrientdsclient.features.review.domain.dto.ReviewRequestDto
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.EditReviewUseCase
import com.danilkha.conentfrientdsclient.features.review.domain.usecase.WriteReviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ReviewEditorViewModel(
    val editReviewUseCase: EditReviewUseCase,
    val writeReviewUseCase: WriteReviewUseCase,
    val contentId: Long
) : ViewModel() {

    val _uiState = MutableStateFlow(EditReviewState())
    val uiState: StateFlow<EditReviewState> = _uiState.asStateFlow()


    fun editMark(mark: Int){
        _uiState.update {
            it.copy(mark = mark)
        }
    }

    fun editText(text: String){
        _uiState.update {
            it.copy(text = text)
        }
    }

    suspend fun save(): Result<Unit> {
        return if(uiState.value.isValid){
            val request = ReviewRequestDto(
                contentId = contentId,
                mark = uiState.value.mark ?: 0,
                text = uiState.value.text
            )
            return when(_uiState.value.mode){
                is EditReviewMode.Edit -> editReviewUseCase(request)
                EditReviewMode.New -> writeReviewUseCase(request)
            }
        } else Result.failure(Exception("not valid"))
    }
}